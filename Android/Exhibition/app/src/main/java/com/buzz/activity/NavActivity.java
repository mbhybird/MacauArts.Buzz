package com.buzz.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.TextView;

import com.buzz.models.extag;
import com.buzz.service.MessageService;
import com.buzz.service.CoreService;
import com.buzz.utils.ConfigHelper;
import com.buzz.utils.GlobalConst;
import com.buzz.utils.VersionUpdateHelper;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by NickChung on 6/8/15.
 */
public class NavActivity extends Activity {
    MyApplication myApp;
    int delayCount = 0;
    ConfigHelper configHelper;
    VersionUpdateHelper versionUpdateHelper;
    DownloadCompleteReceiver completeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        completeReceiver = new DownloadCompleteReceiver();
        /** register download success broadcast **/
        registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        StrictMode.setThreadPolicy(
                new StrictMode
                        .ThreadPolicy
                        .Builder()
                        .detectDiskReads()
                        .detectDiskWrites()
                        .detectNetwork()
                        .penaltyLog()
                        .build());

        StrictMode.setVmPolicy(
                new StrictMode
                        .VmPolicy
                        .Builder()
                        .detectLeakedSqlLiteObjects()
                        .detectLeakedClosableObjects()
                        .penaltyLog()
                        .penaltyDeath()
                        .build());

        myApp = (MyApplication) getApplication();
        configHelper = ConfigHelper.getInstance(this);

        versionUpdateHelper = new VersionUpdateHelper(this);
        //显示版本
        TextView txtVersion = (TextView) findViewById(R.id.view);
        String currentVersion = versionUpdateHelper.getVersionName();
        txtVersion.setText("version:" + currentVersion);

        //检查WIFI和蓝牙
        myApp.checkWifi();
        myApp.checkBluetooth();

        //如果已经登入过则记下登入状态
        if (configHelper.getAppUser() != "") {
            //if (myApp.fileHelper.isFileExist(GlobalConst.PATH_SAVE + GlobalConst.FILENAME_USER_INFO)) {
            myApp.logonUser.isLogon = true;
            //获取显示语言及播放语言
            //初始化用户数据
            myApp.initUserInfo();
            myApp.setDisplayLang(myApp.logonUser.defaultLang, false);
            myApp.setVoiceLang(myApp.logonUser.voiceLang);
        } else {
            //设置默认显示语言
            //如果地区是HK则手动转换成hk,地区是TW则手动转换成tw
            if (Locale.getDefault().getCountry().toLowerCase().equals("hk")) {
                myApp.setDisplayLang("hk", true);
            } else if (Locale.getDefault().getCountry().toLowerCase().equals("tw")) {
                myApp.setDisplayLang("tw", true);
            } else {
                myApp.setDisplayLang(Locale.getDefault().getLanguage(), true);
            }
        }

        //如果有WIFI直接连接服务器
        if (myApp.wifiReady) {
            //连接服务器
            myApp.connectServer();
            //是否更新系统
            if (myApp.serverReady && versionUpdateHelper.isNeedUpdate()) {
                showVersionUpdateDialog();
            } else {
                nextStep();
            }
        } else {
            //如果没有WIFI则打开WIFI
            myApp.openWifi();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //10秒后连接服务器
                    myApp.connectServer();
                    //是否更新系统
                    if (myApp.serverReady && versionUpdateHelper.isNeedUpdate()) {
                        showVersionUpdateDialog();
                    } else {
                        nextStep();
                    }
                }
            }, 10 * 1000);

            delayCount += 10;

        }
    }

    private void nextStep() {
        boolean dataVersionUpdate = false;
        if (myApp.serverReady) {
            //是否第一次更新数据
            if (configHelper.getCatalog() == "") {
                //生成目录文件
                configHelper.updateCatalog();
                //初始化数据版本
                configHelper.updateDataVersion();
            }
            //数据版本更新
            else if (configHelper.isDataVersionUpdate()) {
                dataVersionUpdate = true;
                //获取旧展览目录
                String extagListJson = configHelper.getCatalog();
                if (extagListJson != "") {
                    try {
                        extag[] exTagList = myApp.objectMapper.readValue(extagListJson, extag[].class);
                        if (exTagList != null) {
                            myApp.oldCatalogVersionList.clear();
                            for (extag et : exTagList) {
                                myApp.oldCatalogVersionList.put(et.getExtag(),et.getPublish());
                                //删除旧目录
                                configHelper.deleteExContentFromConfig(et.getExtag());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //更新目录文件
                configHelper.updateCatalog();
                //更新数据版本
                configHelper.updateDataVersion();
            }
        }
        //初始化展览列表
        myApp.initExtagList();

        if (dataVersionUpdate) {
            //如果展览不存在，删除无用的收藏文件和资源文件
            myApp.deleteUselessFiles();
        }

        //初始化beacon列表和action列表
        myApp.initSysParams();

        //如果没有蓝牙
        if (!myApp.btReady) {
            //打开蓝牙
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 0);

            //5秒后再进入
            delayCount += 5;
        } else {
            //3秒后再进入
            delayCount += 3;
        }

        accessRoute(delayCount);
    }

    private void accessRoute(int delayCount) {
        try {
            //显示splash screen
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //如果有用户登录文件
                    if (myApp.logonUser.isLogon) {
                        //启动Beacon服务
                        Intent itCore = new Intent(NavActivity.this, CoreService.class);
                        startService(itCore);

                        //启动后台消息服务
                        Intent itBack = new Intent(NavActivity.this, MessageService.class);
                        startService(itBack);

                        //直接进入首页
                        startActivity(new Intent(NavActivity.this, ExhibitionActivity.class));
                        NavActivity.this.finish();
                    } else {
                        //否则弹出登录界面
                        startActivity(new Intent(NavActivity.this, LoginActivity.class));
                        NavActivity.this.finish();
                    }
                }
            }, delayCount * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showVersionUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NavActivity.this);
        builder.setIcon(R.drawable.logo);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.msg_found_new_app_version));
        builder.setPositiveButton(getString(R.string.msg_dlg_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                if (myApp.fileHelper.isFileExist(GlobalConst.PATH_APK.concat(versionUpdateHelper.getFileName()))) {
                    versionUpdateHelper.install();
                } else {
                    downloadNewPackage();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.msg_dlg_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                nextStep();
            }
        });
        builder.create().show();
    }


    private void downloadNewPackage() {
        DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //创建下载请求
        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(versionUpdateHelper.getmPackageURL()));
        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //后台下载
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //显示下载界面
        down.setVisibleInDownloadsUi(true);
        //设置下载后文件存放的位置
        down.setDestinationInExternalPublicDir(GlobalConst.PATH_APK, versionUpdateHelper.getFileName());
        //将下载请求放入队列
        manager.enqueue(down);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (completeReceiver != null) unregisterReceiver(completeReceiver);
    }

    @Override
    protected void onResume() {
        registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        super.onResume();
    }

    //接受下载完成后的intent
    private class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                //long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                versionUpdateHelper.install();
            }
        }
    }
}
