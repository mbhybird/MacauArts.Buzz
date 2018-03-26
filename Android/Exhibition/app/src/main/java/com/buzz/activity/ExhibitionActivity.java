package com.buzz.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buzz.fonts.FuturaTextView;
import com.buzz.fonts.MinionTextView;
import com.buzz.layout.BidirSlidingLayout;
import com.buzz.layout.CustomDialog;
import com.buzz.layout.CustomProgressDialog;
import com.buzz.models.content;
import com.buzz.models.extag;
import com.buzz.service.BeaconReader;
import com.buzz.service.MessageService;
import com.buzz.service.CoreService;
import com.buzz.utils.GlobalConst;
import com.buzz.models.favorite;
import com.buzz.models.download;
import com.buzz.models.beacon;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.nineoldandroids.animation.ObjectAnimator;


/**
 * Created by buzz on 2015/5/5.
 */
public class ExhibitionActivity extends Activity {
    final static String TAG = ExhibitionActivity.class.getSimpleName();
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    MyApplication myApp;
    ProgressBar progressBar;
    ProgressBar progressBarInit;
    int fileCounter = 0;
    Map<String, MyTask> taskList;
    FuturaTextView title;
    MinionTextView desc;
    RelativeLayout background;
    List<extag> extagList;
    Runnable runnable;
    Handler handler;
    long firstTime = 0;
    Dialog paintDlg;
    boolean canSwipe = false;
    boolean initDone = false;
    MediaPlayer mediaPlayer;
    favorite f;
    favorite delFav;
    download d;
    download delDown;
    Button btnFavor;
    Button btnDownload;
    Button btnIconDownload;
    String lvMode = "f";
    //ProgressDialog mProgressDialog;
    CustomProgressDialog mProgressDialog;
    CustomDialog customDialog;
    BidirSlidingLayout bidirSldingLayout;
    ListView listView;
    GestureDetector detector;
    IntentFilter intentFilter;
    download subDownload;
    HeadsetPlugReceiver headsetPlugReceiver;
    StopMediaReceiver stopMediaReceiver;
    Handler readerHandler;
    Runnable readerRunnable;
    FuturaTextView tvHeaderTitle;
    Button btnMenu;
    RelativeLayout ex_context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exhibition);

        myApp = (MyApplication) getApplication();
        myApp.currentExTagIndex = -1;
        extagList = new ArrayList<extag>();

        readerRunnable = new Runnable() {
            @Override
            public void run() {
                if (!myApp.access) {
                    if (!myApp.downloading) {
                        accessBallViewCheck();
                    }
                    readerHandler.postDelayed(this, 100);
                }
            }
        };
        readerHandler = new Handler();
        readerHandler.post(readerRunnable);

        //收藏对话框
        paintDlg = new Dialog(ExhibitionActivity.this, R.style.PopupDialog);
        paintDlg.setCancelable(false);

        //下载进度对话框
        //mProgressDialog = new ProgressDialog(this);
        mProgressDialog = new CustomProgressDialog(this, R.style.CustomDialog);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage(getString(R.string.msg_file_downloading));
        //mProgressDialog.setIcon(R.drawable.icon_download);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        detector = new GestureDetector(this, new MyGestureDetector());

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });


        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // TODO Auto-generated method stub
                mediaPlayer.release();
                mediaPlayer = null;
                return true;
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });

        listView = (ListView) findViewById(R.id.right_menu_lv);

        //长按
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MyAdspter myAdspter = (MyAdspter) parent.getAdapter();
                Map<String, Object> map = (Map<String, Object>) myAdspter.getItem(position);
                //下载模式
                if (lvMode.equals("d")) {
                    delDown = (download) map.get("download");
                    //下载完成才能进行操作
                    if (delDown.isFinished()) {
                    /*
                    new AlertDialog
                            .Builder(ExhibitionActivity.this)
                            .setTitle(getString(R.string.msg_dlg_title_tips))
                            .setMessage(getString(R.string.msg_delete_all_files))
                            .setPositiveButton(getString(R.string.msg_dlg_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //删除所有相关文件
                                    String[] suffix = new String[]{"_cc.mp3", "_en.mp3", "_sc.mp3", "_pt.mp3"};
                                    for (action ac : delDown.getActionList()) {
                                        if (ac.getClientpath() != null && ac.getServerpath() != null) {
                                            //音频文件需转换为真实文件后删除
                                            if (ac.getContenttype().equals("audio")) {
                                                for (String s : suffix) {
                                                    String realFileName = ac.getFilename().replace(".mp3", s);
                                                    myApp.fileHelper.deleteFile(ac.getClientpath().concat(realFileName));
                                                }
                                            } else {
                                                //图片则直接删除
                                                myApp.fileHelper.deleteFile(ac.getClientpath().concat(ac.getFilename()));
                                            }
                                        }
                                    }

                                    //刷新下载列表
                                    btnDownload.callOnClick();
                                }
                            })
                            .setNegativeButton(getString(R.string.msg_dlg_cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();*/

                        CustomDialog.Builder builder = new CustomDialog.Builder(ExhibitionActivity.this);
                        builder.setTitle(getString(R.string.msg_dlg_title_tips));
                        builder.setMessage(getString(R.string.msg_delete_all_files));
                        builder.setConfirmButton(getString(R.string.msg_dlg_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //删除所有相关文件
                                String[] suffix = new String[]{"_cc.mp3", "_en.mp3", "_sc.mp3", "_pt.mp3"};
                                for (content c : delDown.getContentList()) {
                                    if (c.getClientpath() != null && c.getServerpath() != null) {
                                        //音频文件需转换为真实文件后删除
                                        if (c.getContenttype() == GlobalConst.CONTENT_TYPE_AUDIO) {
                                            for (String s : suffix) {
                                                String realFileName = c.getFilename().replace(".mp3", s);
                                                myApp.fileHelper.deleteFile(c.getClientpath().concat(realFileName));
                                            }
                                        } else {
                                            //图片则直接删除
                                            myApp.fileHelper.deleteFile(c.getClientpath().concat(c.getFilename()));
                                        }
                                    }
                                }

                                //删除配置文件后重新初始化
                                delDown.deleteConfigThenReInit();

                                //更新下载列表
                                myApp.updateDownloadList();

                                //删除相关收藏
                                List<favorite> currentFavList = myApp.getFavoriteList();
                                List<favorite> deleteFavList = new ArrayList<favorite>();

                                for (favorite f : currentFavList) {
                                    if (f.getExtag().equals(delDown.getDextag().getExtag())) {
                                        deleteFavList.add(f);
                                    }
                                }

                                myApp.updateFavoriteList(currentFavList, deleteFavList);

                                //刷新下载列表
                                btnDownload.callOnClick();
                                customDialog.dismiss();
                            }
                        });

                        builder.setBackButton(getString(R.string.msg_dlg_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                customDialog.dismiss();
                            }
                        });

                        customDialog = builder.create();
                        customDialog.show();
                    }
                }
                //收藏模式
                else if (lvMode.equals("f")) {
                    delFav = (favorite) map.get("favorite");
                    CustomDialog.Builder builder = new CustomDialog.Builder(ExhibitionActivity.this);
                    builder.setTitle(getString(R.string.msg_dlg_title_tips));
                    builder.setMessage(getString(R.string.msg_delete_favorite));
                    builder.setConfirmButton(getString(R.string.msg_dlg_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //删除收藏
                            myApp.removeFavoriteFromList(delFav);
                            //刷新收藏列表
                            btnFavor.callOnClick();
                            customDialog.dismiss();
                        }
                    });

                    builder.setBackButton(getString(R.string.msg_dlg_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            customDialog.dismiss();
                        }
                    });

                    customDialog = builder.create();
                    customDialog.show();
                }
                return true;
            }
        });

        //单击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyAdspter myAdspter = (MyAdspter) parent.getAdapter();
                Map<String, Object> map = (Map<String, Object>) myAdspter.getItem(position);

                //收藏模式
                if (lvMode.equals("f")) {
                    f = (favorite) map.get("favorite");
                    String title = "";
                    String artist = "";
                    String imageDesc = "";
                    View dlgView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog, null);
                    content imgContent = myApp.contentList.get(f.getRefImageId());
                    if (imgContent == null) {
                        Toast.makeText(ExhibitionActivity.this, getString(R.string.msg_file_not_exists), Toast.LENGTH_LONG).show();
                        return;
                    }
                    switch (myApp.logonUser.defaultLang) {
                        case GlobalConst.DEFAULT_LANG_CN:
                            title = imgContent.getTitle_cn();
                            artist = imgContent.getArtist_cn();
                            imageDesc = imgContent.getDescription_cn();
                            break;

                        case GlobalConst.DEFAULT_LANG_EN:
                            title = imgContent.getTitle_en();
                            artist = imgContent.getArtist_en();
                            imageDesc = imgContent.getDescription_en();
                            break;

                        case GlobalConst.DEFAULT_LANG_TW:
                            title = imgContent.getTitle_tw();
                            artist = imgContent.getArtist_tw();
                            imageDesc = imgContent.getDescription_tw();
                            break;

                        case GlobalConst.DEFAULT_LANG_PT:
                            title = imgContent.getTitle_pt();
                            artist = imgContent.getArtist_pt();
                            imageDesc = imgContent.getDescription_pt();
                            break;
                    }

                    LinearLayout linearLayout = (LinearLayout) dlgView.findViewById(R.id.dlg_imgView);
                    linearLayout.setBackground(Drawable.createFromPath(
                            GlobalConst.PATH_SDCARD.concat(imgContent.getClientpath()).concat(imgContent.getFilename())));

                    TextView txtTitle = (TextView) dlgView.findViewById(R.id.dlg_txt_title);
                    TextView txtYear = (TextView) dlgView.findViewById(R.id.dlg_txt_year);
                    TextView txtArtist = (TextView) dlgView.findViewById(R.id.dlg_txt_artist);
                    TextView txtDesc = (TextView) dlgView.findViewById(R.id.dlg_txt_desc);
                    Button btnFavor = (Button) dlgView.findViewById(R.id.dlg_favorite_btn);
                    Button btnMenu = (Button) dlgView.findViewById(R.id.dlg_btn_menu);

                    btnFavor.setVisibility(View.INVISIBLE);
                    btnMenu.setVisibility(View.INVISIBLE);

                    txtDesc.setText(imageDesc);
                    txtTitle.setText(title);
                    txtArtist.setText(artist);
                    txtYear.setText(String.valueOf(imgContent.getYear()));

                    ImageButton ibtn = (ImageButton) dlgView.findViewById(R.id.dlg_btnBack);
                    ibtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            paintDlg.dismiss();
                            //停止播放语音
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                            }

                        }
                    });

                    Button btnAudio = (Button) dlgView.findViewById(R.id.dlg_audio_btn);
                    btnAudio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //播放音频
                            content audContent = myApp.contentList.get(f.getRefAudioId());
                            if (audContent == null) {
                                Toast.makeText(ExhibitionActivity.this, getString(R.string.msg_file_not_exists), Toast.LENGTH_LONG).show();
                                return;
                            }
                            playAudio(audContent.getClientpath().concat(audContent.getFilename()));
                        }
                    });

                    paintDlg.setContentView(dlgView);
                    paintDlg.show();
                }
                //下载模式
                else if (lvMode.equals("d")) {
                    d = (download) map.get("download");
                    if (!d.isFinished()) {
                        downloadExFiles(d, map.get("title").toString());
                    }
                }
            }
        });

        btnFavor = (Button) findViewById(R.id.right_menu_btn_favor);
        btnDownload = (Button) findViewById(R.id.right_menu_btn_download);
        btnIconDownload = (Button) findViewById(R.id.exhibition_btn_download);

        progressBar = (ProgressBar) findViewById(R.id.exhibition_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        taskList = new HashMap<String, MyTask>();

        background = (RelativeLayout) findViewById(R.id.exhibition_background);
        title = (FuturaTextView) findViewById(R.id.exhibition_title);
        tvHeaderTitle = (FuturaTextView) findViewById(R.id.right_menu_header_title);
        desc = (MinionTextView) findViewById(R.id.exhibition_desc);
        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canSwipe) {
                    btnMenu.callOnClick();
                }
            }
        });
        ex_context = (RelativeLayout) findViewById(R.id.ex_context);
        ex_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canSwipe) {
                    btnMenu.callOnClick();
                }
            }
        });

        //右侧菜单
        bidirSldingLayout = (BidirSlidingLayout) findViewById(R.id.exhibition);
        btnMenu = (Button) findViewById(R.id.exhibition_btn_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (initDone) {
                        if (bidirSldingLayout.isRightLayoutVisible()) {
                            bidirSldingLayout.scrollToContentFromRightMenu();
                            canSwipe = true;
                        } else {
                            bidirSldingLayout.initShowRightState();
                            bidirSldingLayout.scrollToRightMenu();
                            canSwipe = false;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        progressBarInit = (ProgressBar) findViewById(R.id.exhibition_progress_bar_init);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //myApp.initSysParams();
                //myApp.initExtagList();
                progressBarInit.setVisibility(View.INVISIBLE);
                if (myApp.extagList.size() > 0) {
                    initDone = true;
                    //如果没有展览数据则下载
                    taskList.clear();
                    for (extag et : myApp.extagList.values()) {
                        if (et.getContent() != null) {
                            if (!myApp.fileHelper.isFileExist(et.getContent().getClientpath().concat(et.getContent().getFilename()))) {
                                if (et.getContent().getServerpath() != null) {
                                    taskList.put(et.getContent().getServerpath()
                                            , new MyTask(et.getContent().getClientpath(), et.getContent().getFilename()));
                                }
                            }

                        }

                        extagList.add(et);
                    }

                    if (taskList.size() > 0) {
                        initDone = false;
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(ExhibitionActivity.this, getString(R.string.msg_catalog_loading), Toast.LENGTH_LONG).show();
                    }

                    for (Map.Entry<String, MyTask> entry : taskList.entrySet()) {
                        entry.getValue().execute(entry.getKey());
                    }
                }

                if (initDone) {
                    canSwipe = true;
                    //随机显示一个展览目录
                    if (extagList.size() > 0) {
                        Random rnd = new Random();
                        myApp.currentExTagIndex = rnd.nextInt(extagList.size() - 1);
                        showNextOrPrevEx(1);
                    }

                }
            }
        };

        if (!getIntent().hasExtra("index")) {
            //第一次进入需初始化
            Toast.makeText(ExhibitionActivity.this, getString(R.string.msg_app_initializing), Toast.LENGTH_LONG).show();
            handler.postDelayed(runnable, myApp.initTime * 1000);
        } else {
            //小球返回不需要重新初始化
            progressBarInit.setVisibility(View.INVISIBLE);
            for (extag et : myApp.extagList.values()) {
                extagList.add(et);
            }

            initDone = true;
            canSwipe = true;

            //显示原来展览目录
            if (extagList.size() > 0) {
                myApp.currentExTagIndex = getIntent().getIntExtra("index", 0);
                showNextOrPrevEx(2);
            }
        }

        //收藏列表
        btnFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收藏模式
                lvMode = "f";
                btnFavor.setBackground(getResources().getDrawable(R.drawable.red_love));
                btnDownload.setBackground(getResources().getDrawable(R.drawable.icon_download));
                tvHeaderTitle.setText(getString(R.string.lbl_my_collections));

                String sTitle = "";
                String sDesc = "";
                if (myApp.currentExTagIndex > -1) {
                    extag targetExtag = extagList.get(myApp.currentExTagIndex);
                    switch (myApp.logonUser.defaultLang) {
                        case GlobalConst.DEFAULT_LANG_EN:
                            sTitle = targetExtag.getTitle_en();
                            sDesc = targetExtag.getDescription_en();
                            break;
                        case GlobalConst.DEFAULT_LANG_CN:
                            sTitle = targetExtag.getTitle_cn();
                            sDesc = targetExtag.getDescription_cn();
                            break;
                        case GlobalConst.DEFAULT_LANG_TW:
                            sTitle = targetExtag.getTitle_tw();
                            sDesc = targetExtag.getDescription_tw();
                            break;
                        case GlobalConst.DEFAULT_LANG_PT:
                            sTitle = targetExtag.getTitle_pt();
                            sDesc = targetExtag.getDescription_pt();
                            break;
                    }
                    title.setText(sTitle);
                    desc.setText(sDesc);
                    background.setBackground(Drawable.createFromPath(GlobalConst.PATH_SDCARD
                            .concat(extagList.get(myApp.currentExTagIndex).getContent().getClientpath())
                            .concat(extagList.get(myApp.currentExTagIndex).getContent().getFilename())));

                    //更新收藏列表
                    List<favorite> favoriteList = myApp.getFavoriteList();
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    String itemTitle = "";
                    for (favorite f : favoriteList) {
                        if (f.getExtag().equals(targetExtag.getExtag())) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            content imgContent = myApp.contentList.get(f.getRefImageId());
                            if (imgContent == null) {
                                //itemTitle = getString(R.string.msg_file_not_exists);
                            }
                            else {
                                switch (myApp.logonUser.defaultLang) {
                                    case GlobalConst.DEFAULT_LANG_EN:
                                        itemTitle = imgContent.getTitle_en() + "-" + imgContent.getArtist_en();
                                        break;
                                    case GlobalConst.DEFAULT_LANG_CN:
                                        itemTitle = imgContent.getTitle_cn() + "-" + imgContent.getArtist_cn();
                                        break;
                                    case GlobalConst.DEFAULT_LANG_TW:
                                        itemTitle = imgContent.getTitle_tw() + "-" + imgContent.getArtist_tw();
                                        break;
                                    case GlobalConst.DEFAULT_LANG_PT:
                                        itemTitle = imgContent.getTitle_pt() + "-" + imgContent.getArtist_pt();
                                        break;
                                }

                                map.put("title", itemTitle);
                                map.put("favorite", f);
                                map.put("mode", "f");
                                list.add(map);
                            }
                        }
                    }
                    listView.setAdapter(new MyAdspter(getApplicationContext(), list));
                } else {
                    listView.setAdapter(new MyAdspter(getApplicationContext(), new ArrayList<Map<String, Object>>()));
                }
            }
        });

        //下载列表
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下载模式
                lvMode = "d";
                //选中
                btnDownload.setBackground(getResources().getDrawable(R.drawable.red_download));
                btnFavor.setBackground(getResources().getDrawable(R.drawable.icon_love));
                tvHeaderTitle.setText(getString(R.string.lbl_downloads));

                //更新下载列表
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                String itemTitle = "";
                List<favorite> currentFavList = myApp.getFavoriteList();
                List<String> contentIdList = new ArrayList<String>();
                List<favorite> deleteFavList = new ArrayList<favorite>();
                for (download d : myApp.downloadList) {
                    //如果已经完成下载，检查是否有文件删除及已经收藏
                    if(d.isFinished()) {
                        for (content c : d.getContentList()) {
                            contentIdList.add(c.getContentid());
                        }

                        for (favorite f : currentFavList) {
                            if (f.getExtag().equals(d.getDextag().getExtag())) {
                                if (!contentIdList.contains(f.getRefAudioId())
                                        || !contentIdList.contains(f.getRefImageId())) {
                                    deleteFavList.add(f);
                                }
                            }
                        }

                        myApp.updateFavoriteList(currentFavList, deleteFavList);
                    }

                    Map<String, Object> map = new HashMap<String, Object>();
                    switch (myApp.logonUser.defaultLang) {
                        case GlobalConst.DEFAULT_LANG_EN:
                            itemTitle = d.getDextag().getTitle_en();
                            break;
                        case GlobalConst.DEFAULT_LANG_CN:
                            itemTitle = d.getDextag().getTitle_cn();
                            break;
                        case GlobalConst.DEFAULT_LANG_TW:
                            itemTitle = d.getDextag().getTitle_tw();
                            break;
                        case GlobalConst.DEFAULT_LANG_PT:
                            itemTitle = d.getDextag().getTitle_pt();
                            break;
                    }
                    map.put("title", itemTitle);
                    map.put("download", d);
                    map.put("mode", "d");
                    list.add(map);
                }
                listView.setAdapter(new MyAdspter(getApplicationContext(), list));
            }
        });

        //设置则跳转设置界面
        final Button btnSetting = (Button) findViewById(R.id.right_menu_btn_setting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApp.stopCoreService();
                Intent it = new Intent(ExhibitionActivity.this, SettingActivity.class);
                it.putExtra("root", "ex");
                startActivity(it);
            }
        });

        btnFavor.callOnClick();

        //registerBeaconReceiver();
        registerHeadsetPlugReceiver();
        registerStopMediaReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterBeaconReceiver();
        unregisterReceiver(headsetPlugReceiver);
        unregisterStopMediaReceiver();
    }

    private void downloadExFiles(download down, String dlgTitle) {
        taskList.clear();
        fileCounter = 0;
        String[] suffix = new String[]{"_cc.mp3", "_en.mp3", "_sc.mp3", "_pt.mp3"};

        //保存配置文件并重新初始化
        down.saveToConfigThenReInit();

        for (content c : down.getContentList()) {
            if (c.getClientpath() != null && c.getServerpath() != null) {
                //音频文件需转换为真实路径和文件名
                if (c.getContenttype() == GlobalConst.CONTENT_TYPE_AUDIO) {
                    for (String s : suffix) {
                        String realFileName = c.getFilename().replace(".mp3", s);
                        String realServerPath = c.getServerpath().replace(".mp3", s);
                        if (!myApp.fileHelper.isFileExist(c.getClientpath().concat(realFileName))) {
                            taskList.put(realServerPath, new MyTask(c.getClientpath(), realFileName));
                        }


                    }
                } else {
                    //图片则直接下载
                    if (!myApp.fileHelper.isFileExist(c.getClientpath().concat(c.getFilename()))) {
                        taskList.put(c.getServerpath(), new MyTask(c.getClientpath(), c.getFilename()));
                    }
                }
            }
        }

        if (taskList.size() > 0) {
            mProgressDialog.setTitle(dlgTitle);
            mProgressDialog.setProgress(0);
            mProgressDialog.incrementProgressBy(1);
            mProgressDialog.setMax(taskList.size());
            mProgressDialog.show();
        }
        //只需下载配置
        else {
            //更新下载列表
            myApp.updateDownloadList();
            //刷新左视图
            showNextOrPrevEx(2);
            //刷新下载列表
            btnDownload.callOnClick();
        }

        for (Map.Entry<String, MyTask> entry : taskList.entrySet()) {
            entry.getValue().execute(entry.getKey());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > GlobalConst.SYSTEM_EXIT_INTERVAL) {
                Toast.makeText(ExhibitionActivity.this, getString(R.string.msg_quit_system), Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
                return true;
            } else {
                try {
                    //先停止Beacon服务
                    Intent itCore = new Intent(this, CoreService.class);
                    stopService(itCore);

                    //再停止后台消息服务
                    Intent itBack = new Intent(this, MessageService.class);
                    stopService(itBack);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                this.finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showNextOrPrevEx(int flag) {
        if (extagList.size() > 0) {
            if (flag == 1) {
                //向右滑动
                myApp.currentExTagIndex++;
                if (myApp.currentExTagIndex >= extagList.size()) {
                    myApp.currentExTagIndex = 0;
                }
            } else if (flag == 0) {
                //向左滑动
                myApp.currentExTagIndex--;
                if (myApp.currentExTagIndex < 0) {
                    myApp.currentExTagIndex = extagList.size() - 1;
                }
            } else if (flag == 2) {
                //显示原来图片，不添加逻辑
            }

            if (myApp.currentExTagIndex > -1) {
                //显示或隐藏下载图标
                for (download down : myApp.downloadList) {
                    if (down.getDextag().getExtag().equals(extagList.get(myApp.currentExTagIndex).getExtag())) {
                        if (!down.isFinished()) {
                            subDownload = down;
                            btnIconDownload.setVisibility(View.VISIBLE);
                            btnIconDownload.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    lvMode = "d";
                                    downloadExFiles(subDownload, title.getText().toString());
                                }
                            });
                        } else {
                            btnIconDownload.setVisibility(View.INVISIBLE);
                            btnIconDownload.setOnClickListener(null);
                        }

                        break;
                    } else {
                        btnIconDownload.setVisibility(View.INVISIBLE);
                        btnIconDownload.setOnClickListener(null);
                    }
                }
            }

            //选中收藏按钮
            btnFavor.callOnClick();
        }
    }

    private void playAudio(String audioPath) {
        //停止后台音频
        myApp.stopServiceMedia = true;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        try {
            //检查耳机并播放前台音频
            if (myApp.headSetConnected) {
                mediaPlayer.reset();
                String suffix = "";
                switch (myApp.logonUser.voiceLang) {
                    case GlobalConst.VOICE_LANG_CC:
                        suffix = "_cc.mp3";
                        break;

                    case GlobalConst.VOICE_LANG_SC:
                        suffix = "_sc.mp3";
                        break;

                    case GlobalConst.VOICE_LANG_EN:
                        suffix = "_en.mp3";
                        break;

                    case GlobalConst.VOICE_LANG_PT:
                        suffix = "_pt.mp3";
                        break;
                }
                mediaPlayer.setDataSource(GlobalConst.PATH_SDCARD.concat(audioPath.replace(".mp3", suffix)));
                mediaPlayer.prepare();
            } else {
                Toast.makeText(ExhibitionActivity.this, getString(R.string.msg_conn_headset), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerStopMediaReceiver() {
        stopMediaReceiver = new StopMediaReceiver();
        intentFilter = new IntentFilter(GlobalConst.ACTION_STOP_MEDIA);
        registerReceiver(stopMediaReceiver, intentFilter);
    }

    private void unregisterStopMediaReceiver() {
        if (stopMediaReceiver != null) {
            unregisterReceiver(stopMediaReceiver);
        }
    }

//    private void unregisterBeaconReceiver() {
//        if (myBeaconReceiver != null) {
//            unregisterReceiver(myBeaconReceiver);
//        }
//    }
//
//    private void registerBeaconReceiver() {
//        myBeaconReceiver = new MyBeaconReceiver();
//        intentFilter = new IntentFilter(CoreService.ACTION_BEACON_SEND);
//        registerReceiver(myBeaconReceiver, intentFilter);
//    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        private String clientPath;
        private String fileName;

        protected MyTask(String clientPath, String fileName) {
            this.clientPath = clientPath;
            this.fileName = fileName;
        }

        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            //Log.i(TAG, "onPreExecute() called");
            myApp.downloading = true;
            myApp.stopCoreService();
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            //Log.i(TAG, "doInBackground(Params... params) called");
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(params[0]);
                HttpResponse response = client.execute(get);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    InputStream is = entity.getContent();
                    long total = entity.getContentLength();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024 * 10];
                    int count = 0;
                    int length = -1;
                    while ((length = is.read(buf)) != -1) {
                        baos.write(buf, 0, length);
                        count += length;
                        //调用publishProgress公布进度,最后onProgressUpdate方法将被执行
                        publishProgress((int) ((count / (float) total) * 100));
                        //为了演示进度,休眠500毫秒
                        //Thread.sleep(5);
                    }

                    //保存文件
                    String filePath = GlobalConst.PATH_SDCARD + this.clientPath;
                    String fileName = this.fileName;
                    String saveTo = filePath + fileName;
                    File file = new File(filePath);
                    file.mkdirs();
                    file = null;
                    file = new File(saveTo);
                    file.createNewFile();
                    OutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(baos.toByteArray());
                    baos.close();
                    baos.flush();
                    outputStream.close();
                    outputStream.flush();
                    file = null;

                    return this.fileName;
                }
            } catch (Exception e) {
                //Log.i(TAG, e.getMessage());
            }
            return null;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            //Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
            if (lvMode.equals("d")) {
            } else {
                progressBar.setProgress(progresses[0]);
            }

        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            fileCounter++;
            if (lvMode.equals("d")) {
                mProgressDialog.setProgress(fileCounter);
            } else {
                progressBar.setSecondaryProgress((int) ((fileCounter * 1.0 / taskList.size()) * 100));
            }

            if (fileCounter == taskList.size()) {
                if (lvMode.equals("d")) {
                    //关闭下载对话框
                    mProgressDialog.setProgress(0);
                    mProgressDialog.dismiss();
                    //更新下载列表
                    myApp.updateDownloadList();
                    //刷新下载列表
                    btnDownload.callOnClick();
                    //隐藏下载图标
                    btnIconDownload.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ExhibitionActivity.this, getString(R.string.msg_catalog_finished), Toast.LENGTH_LONG).show();
                }

                initDone = true;
                canSwipe = !bidirSldingLayout.isRightLayoutVisible();
                myApp.downloading = false;
                myApp.startCoreService();
            }
        }
    }

    public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (canSwipe) {
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    //right
                    /*
                    ViewHelper.setPivotX(bidirSldingLayout, bidirSldingLayout.getWidth() / 2);
                    ViewHelper.setPivotY(bidirSldingLayout, bidirSldingLayout.getHeight() / 2);
                    ObjectAnimator.ofFloat(bidirSldingLayout, "rotationY", 0, 90).setDuration(1000).start();*/
                    ObjectAnimator.ofFloat(bidirSldingLayout, "translationX", bidirSldingLayout.getWidth(), 0).setDuration(500).start();

                    showNextOrPrevEx(1);

                    //ObjectAnimator.ofFloat(bidirSldingLayout, "rotationY", 90, 0).setDuration(1000).start();

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    //left
                    /*
                    ViewHelper.setPivotX(bidirSldingLayout, bidirSldingLayout.getWidth() / 2);
                    ViewHelper.setPivotY(bidirSldingLayout, bidirSldingLayout.getHeight() / 2);
                    ObjectAnimator.ofFloat(bidirSldingLayout, "rotationY", 0, -90).setDuration(1000).start();*/
                    ObjectAnimator.ofFloat(bidirSldingLayout, "translationX", -bidirSldingLayout.getWidth(), 0).setDuration(500).start();

                    showNextOrPrevEx(0);

                    //ObjectAnimator.ofFloat(bidirSldingLayout, "rotationY", -90, 0).setDuration(1000).start();

                }
            }

            return true;
        }

        /*
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }*/
    }

    private void accessBallViewCheck() {
        for (Map.Entry<String, BeaconReader> entry : myApp.readerMap.entrySet()) {
            //如果检测到某展览Beacon则跳转到小球页面
            beacon b = myApp.beaconList.get(entry.getKey());
            if (b != null) {
                if (myApp.isBeaconResourceReady(b.getTriggercontent())) {
                    if (entry.getValue().getRssi() >= GlobalConst.RANGE_ACCESS_BALL_VIEW
                            && b.getUsage().equals(GlobalConst.BEACON_USAGE_DETAIL)) {

                        //切换展览索引
                        for (int i = 0; i < extagList.size(); i++) {
                            if (extagList.get(i).getExtag().equals(b.getExtag())) {
                                myApp.currentExTagIndex = i;
                                break;
                            }
                        }

                        //跳转到小球页面
                        Intent it = new Intent();
                        it.setClass(ExhibitionActivity.this, MainActivity.class);
                        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(it);

                        myApp.access = true;
                        Log.i(TAG, "INDOOR");

                        //停止播放语音
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }

                        ExhibitionActivity.this.finish();
                    }
                }
            }
        }
    }

    private void registerHeadsetPlugReceiver() {
        headsetPlugReceiver = new HeadsetPlugReceiver();
        IntentFilter filter = new IntentFilter(GlobalConst.ACTION_HEADSET_PLUG);
        registerReceiver(headsetPlugReceiver, filter);
    }

    public class HeadsetPlugReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.hasExtra("state")) {
                if (intent.getIntExtra("state", 0) == 0) {
                    Log.i(TAG, "headset not connected");
                    myApp.headSetConnected = false;
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                } else if (intent.getIntExtra("state", 0) == 1) {
                    Log.i(TAG, "headset connected");
                    myApp.headSetConnected = true;
                }
            }
        }

    }

    public class StopMediaReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            myApp.stopServiceMedia = true;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        }
    }
}
