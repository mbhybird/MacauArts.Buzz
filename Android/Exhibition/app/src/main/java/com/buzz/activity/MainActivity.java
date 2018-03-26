package com.buzz.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buzz.fonts.FuturaTextView;
import com.buzz.layout.BidirSlidingLayout;
import com.buzz.layout.CustomDialog;
import com.buzz.layout.CustomProgressDialog;
import com.buzz.models.MyCircle;
import com.buzz.models.beacon;
import com.buzz.models.content;
import com.buzz.models.download;
import com.buzz.models.extag;
import com.buzz.models.favorite;
import com.buzz.service.BeaconReader;
import com.buzz.service.MessageService;
import com.buzz.service.CoreService;
import com.buzz.shape.CircleImageView;
import com.buzz.utils.GlobalConst;
import com.buzz.utils.GlobalConst.AppRunningState;
import com.buzz.utils.ImageHelper;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

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
import java.util.*;

public class MainActivity extends ActionBarActivity {

    final String TAG = this.getClass().getSimpleName();
    MyApplication myApp;
    IntentFilter intentFilter;
    MediaPlayer mediaPlayer;
    RelativeLayout rootLayout;
    RelativeLayout menuLayout;
    RelativeLayout ballLayout;
    String bid;
    String state;
    long firstTime = 0;
    List<MyCircle> myCircleList;
    int screenWidth;
    int screenHeight;
    DisplayMetrics dm;
    CircleImageView c;
    int animInterval = 0;
    String circleBid;
    Dialog paintDlg;
    String lastShowTag;
    Handler dlgHandler;
    Runnable dlgRunnable;
    ValueAnimator anim;
    String imageDesc = "";
    String title = "";
    String artist = "";
    String year = "";
    String mExtag = "";
    TextView tvBoard;
    Message ballCountMsg;
    Handler ballCountHandler;
    Handler returnToExHandler;
    Runnable returnToExRunnable;
    boolean returnToExFlag;
    BidirSlidingLayout bidirSldingLayout;
    boolean canReceiveSignal = true;
    favorite f;
    favorite delFav;
    download d;
    download delDown;
    Button btnFavor;
    Button btnDownload;
    String lvMode = "f";
    download subDownload;
    ListView listView;
    Dialog favDlg;
    CustomProgressDialog mProgressDialog;
    CustomDialog customDialog;
    Map<String, MyTask> taskList;
    int fileCounter = 0;
    List<extag> extagList;
    StopMediaReceiver stopMediaReceiver;
    List<DlgEvent> showDlgTargetList;
    int noBeaconCount = 0;
    Handler readerHandler;
    Runnable readerRunnable;
    FuturaTextView tvHeaderTitle;
    RelativeLayout boardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myApp = (MyApplication) getApplication();

        readerRunnable = new Runnable() {
            @Override
            public void run() {
                if (myApp.access) {
                    updateBallView();
                    readerHandler.postDelayed(this, 100);
                }
            }
        };
        readerHandler = new Handler();
        readerHandler.post(readerRunnable);

        //初始化对话框列表
        showDlgTargetList = new ArrayList<DlgEvent>();

        //初始化下载任务列表
        taskList = new HashMap<String, MyTask>();

        //初始化展览列表
        extagList = new ArrayList<extag>();
        for (extag et : myApp.extagList.values()) {
            extagList.add(et);
        }

        //下载进度对话框
        mProgressDialog = new CustomProgressDialog(this, R.style.CustomDialog);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage(getString(R.string.msg_file_downloading));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        //面版计数器
        tvBoard = (TextView) findViewById(R.id.main_activity_txt_ball_count);
        ballCountHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        tvBoard.setText(msg.obj.toString());
                        break;
                }
            }
        };

        //画作详细窗口
        paintDlg = new Dialog(MainActivity.this, R.style.PopupDialog);
        paintDlg.setCancelable(false);

        //监听窗口状态，如果超出响应范围则关闭
        dlgRunnable = new Runnable() {
            @Override
            public void run() {
                //取显示队列的头部无素
                if (showDlgTargetList.size() > 0) {
                    DlgEvent dlgEvent = showDlgTargetList.get(0);
                    //如果是自动操作
                    if (dlgEvent.mode == 1) {
                        if (myApp.beaconStateList.containsKey(lastShowTag)) {
                            if (paintDlg.isShowing()) {
                                if (myApp.beaconStateList.get(lastShowTag) == GlobalConst.TRIGGER_TYPE_OUT) {
                                    if (mediaPlayer.isPlaying()) {
                                        mediaPlayer.stop();
                                        mediaPlayer.reset();
                                    }
                                    try {
                                        paintDlg.dismiss();
                                        //清除列表
                                        showDlgTargetList.clear();
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                showPaintDialog(dlgEvent.beaconid);
                            }
                        } else {
                            showPaintDialog(dlgEvent.beaconid);
                        }
                    } else {
                        //如果是手动操作
                        showPaintDialog(dlgEvent.beaconid);
                    }
                }
                dlgHandler.postDelayed(this, 500);
            }
        };

        dlgHandler = new Handler();
        dlgHandler.post(dlgRunnable);

        myCircleList = new ArrayList<MyCircle>();
        dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        myApp.scaleVector = dm.densityDpi / 240f;
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        rootLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        menuLayout = (RelativeLayout) findViewById(R.id.main_activity_menu_layout);
        ballLayout = (RelativeLayout) findViewById(R.id.main_activity_ball_layout);

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

        //registerBeaconReceiver();
        registerStopMediaReceiver();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("bid")) {
                bid = bundle.getString("bid");
                if (myApp.playHistoryList.containsKey(bid)) {
                    //showPaintDialog(bid);
                }
            }
        }

        //退回展览界面
        returnToExFlag = false;
        returnToExHandler = new Handler();
        returnToExRunnable = new Runnable() {
            @Override
            public void run() {
                if (returnToExFlag) {
                    Intent it = new Intent();
                    it.setClass(MainActivity.this, ExhibitionActivity.class);
                    it.putExtra("index", myApp.currentExTagIndex);
                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(it);
                    MainActivity.this.finish();
                } else {
                    returnToExHandler.postDelayed(this, 100);
                }
            }
        };

        returnToExHandler.post(returnToExRunnable);

        btnFavor = (Button) findViewById(R.id.right_menu_btn_favor);
        btnDownload = (Button) findViewById(R.id.right_menu_btn_download);
        tvHeaderTitle = (FuturaTextView) findViewById(R.id.right_menu_header_title);

        //右侧菜单
        bidirSldingLayout = (BidirSlidingLayout) findViewById(R.id.mainSlidingLayout);
        Button btnMenu = (Button) findViewById(R.id.main_activity_btn_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bidirSldingLayout.isRightLayoutVisible()) {
                    bidirSldingLayout.scrollToContentFromRightMenu();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            canReceiveSignal = true;
                        }
                    }, 1000);
                } else {
                    bidirSldingLayout.initShowRightState();
                    bidirSldingLayout.scrollToRightMenu();
                    canReceiveSignal = false;
                    btnFavor.callOnClick();
                }
            }
        });

        boardLayout = (RelativeLayout) findViewById(R.id.main_activity_board_layout);
        boardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canReceiveSignal) {
                    btnMenu.callOnClick();
                }
            }
        });
        menuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canReceiveSignal) {
                    btnMenu.callOnClick();
                }
            }
        });
        ballLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canReceiveSignal) {
                    btnMenu.callOnClick();
                }
            }
        });

        //收藏对话框
        favDlg = new Dialog(MainActivity.this, R.style.PopupDialog);

        //右侧菜单列表
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
                        CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
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
                    CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
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
                        Toast.makeText(MainActivity.this, getString(R.string.msg_file_not_exists), Toast.LENGTH_LONG).show();
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
                            favDlg.dismiss();
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
                                Toast.makeText(MainActivity.this, getString(R.string.msg_file_not_exists), Toast.LENGTH_LONG).show();
                                return;
                            }
                            playAudio(audContent.getClientpath().concat(audContent.getFilename()));
                        }
                    });

                    favDlg.setContentView(dlgView);
                    favDlg.show();
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

        //收藏列表
        btnFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收藏模式
                lvMode = "f";
                btnFavor.setBackground(getResources().getDrawable(R.drawable.red_love));
                btnDownload.setBackground(getResources().getDrawable(R.drawable.icon_download));
                tvHeaderTitle.setText(getString(R.string.lbl_my_collections));

                //更新收藏列表
                List<favorite> favoriteList = myApp.getFavoriteList();
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                String itemTitle = "";
                if (myApp.currentExTagIndex > -1) {
                    extag targetExtag = extagList.get(myApp.currentExTagIndex);
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
                Intent it = new Intent(MainActivity.this, SettingActivity.class);
                it.putExtra("root", "main");
                startActivity(it);
            }
        });

        btnFavor.callOnClick();
    }

    private void downloadExFiles(download down, String dlgTitle) {
        taskList.clear();
        fileCounter = 0;

        //保存配置文件并重新初始化
        down.saveToConfigThenReInit();

        String[] suffix = new String[]{"_cc.mp3", "_en.mp3", "_sc.mp3", "_pt.mp3"};
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
            //刷新下载列表
            btnDownload.callOnClick();
        }

        for (Map.Entry<String, MyTask> entry : taskList.entrySet()) {
            entry.getValue().execute(entry.getKey());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > GlobalConst.SYSTEM_EXIT_INTERVAL) {
                Toast.makeText(MainActivity.this, getString(R.string.msg_quit_system), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        /*
        if (myApp.wifiReady && myApp.btReady && myApp.serverReady && myApp.logonUser.isLogon) {
            Intent intent = new Intent(this, BackgroundService.class);
            startService(intent);
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterBeaconReceiver();
        unregisterStopMediaReceiver();

        //Intent intent = new Intent(this, BackgroundService.class);
        //stopService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerHomeKeyReceiver(this);
        Intent it = new Intent(GlobalConst.ACTION_APP_RUNNING_MONITOR);
        it.putExtra("BOF", AppRunningState.FRONT.ordinal());
        sendBroadcast(it);
    }

    @Override
    protected void onPause() {
        //unregisterHomeKeyReceiver(this);
        Intent it = new Intent(GlobalConst.ACTION_APP_RUNNING_MONITOR);
        it.putExtra("BOF", AppRunningState.BACK.ordinal());
        sendBroadcast(it);
        super.onPause();
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

    /*
    protected void registerHomeKeyReceiver(Context context) {
        //Log.i(TAG, "registerHomeKeyReceiver");
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    protected void unregisterHomeKeyReceiver(Context context) {
        //Log.i(TAG, "unregisterHomeKeyReceiver");
        if (null != mHomeKeyReceiver) {
            context.unregisterReceiver(mHomeKeyReceiver);
        }
    }*/

    private void showPaintDialog(String bid) {
        //如果列表存在
        if (myApp.actionList.containsKey(bid)) {
            //如果已经在显示中
            if (paintDlg.isShowing()) {
                //如果显示的ID不一样则关闭当前窗口
                if (!lastShowTag.equals(bid)) {
                    paintDlg.dismiss();
                    //停止播放语音
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                }
            } else {
                //如果未显示则找出对应的图片、说明、音频并播放(播放前先停止后台播放音频及当前播放音频)
                View dlgView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog, null);

                mExtag = myApp.beaconList.get(bid).getExtag();
                Iterator<content> it = myApp.actionList.get(bid).iterator();
                final favorite fav = new favorite();
                fav.setExtag(mExtag);

                //查找匹配项
                while (it.hasNext()) {
                    content c = it.next();
                    if (c.getContenttype() == GlobalConst.CONTENT_TYPE_IMAGE) {
                        fav.setRefImageId(c.getContentid());
                        year = String.valueOf(c.getYear());
                        switch (myApp.logonUser.defaultLang) {
                            case GlobalConst.DEFAULT_LANG_CN:
                                title = c.getTitle_cn();
                                artist = c.getArtist_cn();
                                imageDesc = c.getDescription_cn();
                                break;

                            case GlobalConst.DEFAULT_LANG_EN:
                                title = c.getTitle_en();
                                artist = c.getArtist_en();
                                imageDesc = c.getDescription_en();
                                break;

                            case GlobalConst.DEFAULT_LANG_TW:
                                title = c.getTitle_tw();
                                artist = c.getArtist_tw();
                                imageDesc = c.getDescription_tw();
                                break;

                            case GlobalConst.DEFAULT_LANG_PT:
                                title = c.getTitle_pt();
                                artist = c.getArtist_pt();
                                imageDesc = c.getDescription_pt();
                                break;
                        }
                    } else if (c.getContenttype() == GlobalConst.CONTENT_TYPE_AUDIO) {
                        fav.setRefAudioId(c.getContentid());
                    }
                }

                LinearLayout linearLayout = (LinearLayout) dlgView.findViewById(R.id.dlg_imgView);
                content imgContent = myApp.contentList.get(fav.getRefImageId());
                if (imgContent == null) {
                    Toast.makeText(MainActivity.this, getString(R.string.msg_file_not_exists), Toast.LENGTH_LONG).show();
                    return;
                }
                linearLayout.setBackground(Drawable.createFromPath(
                        GlobalConst.PATH_SDCARD.concat(imgContent.getClientpath()).concat(imgContent.getFilename())));

                TextView txtTitle = (TextView) dlgView.findViewById(R.id.dlg_txt_title);
                TextView txtYear = (TextView) dlgView.findViewById(R.id.dlg_txt_year);
                TextView txtArtist = (TextView) dlgView.findViewById(R.id.dlg_txt_artist);
                TextView txtDesc = (TextView) dlgView.findViewById(R.id.dlg_txt_desc);

                //设置可滚动,自定义布局已实现该功能
//              txtDesc.setMovementMethod(android.text.method.ScrollingMovementMethod.getInstance());

                txtDesc.setText(imageDesc);
                txtTitle.setText(title);
                txtArtist.setText(artist);
                txtYear.setText(year);

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
                        //清除列表
                        showDlgTargetList.clear();
                    }
                });

                Button btnAudio = (Button) dlgView.findViewById(R.id.dlg_audio_btn);
                btnAudio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //播放音频
                        content audContent = myApp.contentList.get(fav.getRefAudioId());
                        if (audContent == null) {
                            Toast.makeText(MainActivity.this, getString(R.string.msg_file_not_exists), Toast.LENGTH_LONG).show();
                            return;
                        }
                        playAudio(audContent.getClientpath().concat(audContent.getFilename()));
                    }
                });

                final Button btnFavorite = (Button) dlgView.findViewById(R.id.dlg_favorite_btn);
                Button btnMenu = (Button) dlgView.findViewById(R.id.dlg_btn_menu);
                btnMenu.setVisibility(View.INVISIBLE);

                //已收藏显示实心图标
                if (findFavoriteInList(fav)) {
                    btnFavorite.setBackground(getResources().getDrawable(R.drawable.red_love));
                } else {
                    //未收藏显示空心图标并添加收藏事件
                    btnFavorite.setBackground(getResources().getDrawable(R.drawable.icon_love));
                    btnFavorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                myApp.addToFavorite(myApp.objectMapper.writeValueAsString(fav) + ",");
                                Toast.makeText(MainActivity.this, getString(R.string.msg_add_to_favorite_success), Toast.LENGTH_LONG).show();
                                btnFavorite.setBackground(getResources().getDrawable(R.drawable.red_love));
                                btnFavorite.setOnClickListener(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                //显示自定义样式窗口dialog.xml + <style name="PopupDialog">
                paintDlg.setContentView(dlgView);
                paintDlg.show();
                lastShowTag = bid;

                //是否自动播放
                if (myApp.logonUser.autoPlay.equals(GlobalConst.AUTO_PLAY_ON)) {
                    //播放音频
                    content audContent = myApp.contentList.get(fav.getRefAudioId());
                    if (audContent == null) {
                        Toast.makeText(MainActivity.this, getString(R.string.msg_file_not_exists), Toast.LENGTH_LONG).show();
                        return;
                    }
                    playAudio(audContent.getClientpath().concat(audContent.getFilename()));
                }

            }
        }
    }

    private boolean findFavoriteInList(favorite fav) {
        for (favorite f : myApp.getFavoriteList()) {
            if (f.equals(fav)) {
                return true;
            }
        }

        return false;
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
                Toast.makeText(MainActivity.this, getString(R.string.msg_conn_headset), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class RandomString {
        /**
         * 随机排序
         *
         * @param list 要进行随机排序的数据集合(小球出现的四个角落）
         * @return 排序结果
         */
        public static String[] ballPost = new String[]{"TL", "TR", "BR", "BL"};

        public static String[] getList(String[] list) {
            //数组长度
            int count = list.length;
            //结果集
            String[] resultList = new String[count];
            //用一个LinkedList作为中介
            LinkedList<String> temp = new LinkedList<String>();
            //初始化temp
            for (int i = 0; i < count; i++) {
                temp.add(list[i].toString());
            }
            //取数
            Random rand = new Random();
            for (int i = 0; i < count; i++) {
                int num = rand.nextInt(count - i);
                resultList[i] = temp.get(num).toString();
                temp.remove(num);
            }

            return resultList;
        }
    }

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
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            fileCounter++;
            mProgressDialog.setProgress(fileCounter);

            if (fileCounter == taskList.size()) {
                //关闭下载对话框
                mProgressDialog.setProgress(0);
                mProgressDialog.dismiss();
                //更新下载列表
                myApp.updateDownloadList();
                //刷新下载列表
                btnDownload.callOnClick();
                myApp.startCoreService();
            }
        }
    }

//    private class MyBeaconReceiver extends BeaconReceiver {
//        @Override
//        public void Do(Map<String, BeaconReader> map) {

    private void updateBallView() {
        //切换右边菜单则停止检测信号
        if (!canReceiveSignal) return;

        //如果不满足返回条件则刷新小球视图
        if (!returnToExFlag) {
            try {
                //检测相关展览Beacon
                int beaconFound = 0;
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, BeaconReader> entry : myApp.readerMap.entrySet()) {
                    sb.append(entry.getKey() + "," + entry.getValue().getRssi() + "\r\n");
                    beacon b = myApp.beaconList.get(entry.getKey());
                    if (b != null) {
                        if (myApp.isBeaconResourceReady(b.getTriggercontent())) {
                            if (entry.getValue().getRssi() >= GlobalConst.RANGE_QUIT_BALL_VIEW
                                    && b.getUsage().equals(GlobalConst.BEACON_USAGE_DETAIL)) {
                                beaconFound++;
                            }
                        }
                    }
                }

                //如果没检测相关Beacon则跳回展览页面
                if (beaconFound == 0) {
                    noBeaconCount++;
                    if (noBeaconCount >= 10) {
                        Log.i(TAG, "OUTDOOR");
                        myApp.access = false;
                        noBeaconCount = 0;
                        returnToExFlag = true;
                    }
                    return;
                }

                if (animInterval % GlobalConst.ANIMATION_INTERVAL == 0) {
                    ballLayout.removeAllViews();
                    ballLayout.invalidate();
                    ballCountMsg = new Message();
                    ballCountMsg.what = 1;
                    ballCountMsg.obj = 0;
                    ballCountHandler.sendMessage(ballCountMsg);
                }

                String title;
                String imagePath;
                String tag;
                myCircleList.clear();

                for (Map.Entry<String, BeaconReader> m : myApp.readerMap.entrySet()) {
                    beacon b = myApp.beaconList.get(m.getKey());
                    //beacon须为前台或所有且用途为画作
                    if (b != null) {
                        if (myApp.isBeaconResourceReady(b.getTriggercontent())) {
                            String direction = b.getRangedirection();
                            if ((direction.equals(GlobalConst.RANGE_DIRECTION_FRONT)
                                    || direction.equals(GlobalConst.RANGE_DIRECTION_BOTH))
                                    && b.getUsage().equals(GlobalConst.BEACON_USAGE_DETAIL)) {
                                int colorRes = R.color.blue;
                                title = "";
                                imagePath = "";
                                tag = b.getDisplayname();
                                int rangeIn = b.getEffectiverangein();
                                //如果信号足够强，直接跳转作品详细界面
                                if (m.getValue().getRssi() >= rangeIn) {
                                    //进显示队列头部
                                    showDlgTargetList.add(0, new DlgEvent(m.getKey(), 1));
                                    //showPaintDialog(m.getKey());
                                } else if (m.getValue().getRssi() >= rangeIn + GlobalConst.RSSI_COLOR_LEVEL_01) {
                                    //一级小球信号递减
                                    colorRes = R.color.level01;
                                    Iterator it = myApp.actionList.get(m.getKey()).iterator();
                                    //小球显示标题
                                    while (it.hasNext()) {
                                        content c = (content) it.next();
                                        if (c.getContenttype() == GlobalConst.CONTENT_TYPE_IMAGE) {
                                            switch (myApp.logonUser.defaultLang) {
                                                case GlobalConst.DEFAULT_LANG_CN:
                                                    title = c.getTitle_cn() + "," + c.getArtist_cn();
                                                    break;

                                                case GlobalConst.DEFAULT_LANG_EN:
                                                    title = c.getTitle_en() + "," + c.getArtist_en();
                                                    break;

                                                case GlobalConst.DEFAULT_LANG_TW:
                                                    title = c.getTitle_tw() + "," + c.getArtist_tw();
                                                    break;

                                                case GlobalConst.DEFAULT_LANG_PT:
                                                    title = c.getTitle_pt() + "," + c.getArtist_pt();
                                                    break;
                                            }
                                            imagePath = GlobalConst.PATH_SDCARD.concat(c.getClientpath()).concat(c.getFilename());
                                        }
                                    }
                                } else if (m.getValue().getRssi() >= rangeIn + GlobalConst.RSSI_COLOR_LEVEL_02) {
                                    //二级小球信号递减
                                    colorRes = R.color.level02;
                                } else if (m.getValue().getRssi() >= rangeIn + GlobalConst.RSSI_COLOR_LEVEL_03) {
                                    //三级小球信号递减
                                    colorRes = R.color.level03;
                                } else if (m.getValue().getRssi() >= rangeIn + GlobalConst.RSSI_COLOR_LEVEL_04) {
                                    //三级小球信号递减
                                    colorRes = R.color.level04;
                                }

                                //至少在最低RSSI范围内才添加小球
                                if (m.getValue().getRssi() >= rangeIn + GlobalConst.RSSI_COLOR_LEVEL_04) {
                                    myCircleList.add(new MyCircle(colorRes, tag, title, imagePath, m.getKey(), getApplication()));

                                }
                            }
                        }
                    }
                }

                    /*小球列表根据信号排序，并限制显示数量*/
                Collections.sort(myCircleList);

                //间隔内显示信号小球，点击跳转作品详细界面
                if (animInterval % GlobalConst.ANIMATION_INTERVAL == 0) {
                    int elementCount = 0;
                    String[] targetPosList = RandomString.getList(RandomString.ballPost);
                    String targetPos = "";
                    for (MyCircle mc : myCircleList) {
                        if (elementCount < GlobalConst.ANIMATION_ELEMENT_LIMITED) {
                            elementCount++;
                            c = new CircleImageView(getApplicationContext());
                            circleBid = mc.BeaconId;

                            Bitmap bm = null;
                            int borderWidth = 0;
                            int textSize = 0;
                            switch (mc.ColorRes) {
                                case R.color.level01:
                                    //显示画作图片
                                    bm = ImageHelper.getLocalBitmap(mc.ImagePath);
                                    //加大边框
                                    borderWidth = 10;
                                    //文字大小
                                    textSize = 40;
                                    break;

                                case R.color.level02:
                                    //显示小球信号图片
                                    bm = ImageHelper.readBitMap(getApplicationContext(), R.drawable.ball_level02);
                                    //bm = BitmapFactory.decodeResource(getResources(), R.drawable.ball_level02);
                                    textSize = 35;
                                    break;

                                case R.color.level03:
                                    //显示小球信号图片
                                    bm = ImageHelper.readBitMap(getApplicationContext(), R.drawable.ball_level03);
                                    //bm = BitmapFactory.decodeResource(getResources(), R.drawable.ball_level03);
                                    textSize = 30;
                                    break;

                                case R.color.level04:
                                    //显示小球信号图片
                                    bm = ImageHelper.readBitMap(getApplicationContext(), R.drawable.ball_level04);
                                    //bm = BitmapFactory.decodeResource(getResources(), R.drawable.ball_level04);
                                    textSize = 25;
                                    break;
                            }

                            //标题＋作者
                            c.setTitle(mc.Title);
                            //标签
                            c.setTag(mc.Tag);
                            //文字大小
                            c.setTextSize(textSize);
                            //背景
                            c.setImageBitmap(bm);
                            //边框
                            c.setBorderWidth(borderWidth);
                            //边框颜色
                            c.setBorderColor(getResources().getColor(mc.ColorRes));
                            //出现位置
                            RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(mc.Size, mc.Size);
                            switch (elementCount) {
                                case 1:
                                    //居中
                                    lps.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

                                    //偏移动画
                                    ViewHelper.setPivotX(c, 0);
                                    ViewHelper.setPivotY(c, 0);
                                    Random rndX = new Random();
                                    Random rndY = new Random();
                                    Random rndSX = new Random();
                                    Random rndSY = new Random();
                                    int offsetX = rndX.nextInt(10);
                                    int offsetY = rndY.nextInt(10);
                                    int symbolX = rndSX.nextInt(2);
                                    int symbolY = rndSY.nextInt(2);
                                    symbolX = (symbolX % 2 == 0) ? -1 : 1;
                                    symbolY = (symbolY % 2 == 0) ? 1 : -1;
                                    AnimatorSet set = new AnimatorSet();
                                    set.playTogether(
                                            ObjectAnimator.ofFloat(c, "translationX", 0, symbolX * offsetX, -1 * symbolX * offsetX, 0)
                                            , ObjectAnimator.ofFloat(c, "translationY", 0, symbolY * offsetY, -1 * symbolY * offsetY, 0)
                                    );
                                    set.setDuration(1000).start();
                                    break;

                                case 2:
                                    targetPos = targetPosList[0];
                                    break;

                                case 3:
                                    targetPos = targetPosList[1];
                                    break;

                                case 4:
                                    targetPos = targetPosList[2];
                                    break;

                                case 5:
                                    targetPos = targetPosList[3];
                                    break;
                            }

                            //随机出现位置
                            switch (targetPos) {
                                //左上角
                                case "TL":
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                                    break;

                                //右上角
                                case "TR":
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                                    break;

                                //左下角
                                case "BL":
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                                    break;

                                //右下角
                                case "BR":
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                                    lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                                    break;
                            }

                            c.setLayoutParams(lps);
                            c.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                        //进显示队列头部
                                        showDlgTargetList.add(0, new DlgEvent(circleBid, 0));
                                        //showPaintDialog(circleBid);
                                    }
                                    return true;
                                }
                            });

                                /*
                                c.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showPaintDialog(circleBid);
                                    }
                                });*/

                                /*
                                anim = ObjectAnimator.ofFloat(c, "alpha", 1.0f, 0.5f)
                                        .setDuration(GlobalConst.ANIMATION_DURATION);

                                anim.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        layout.removeView((CircleImageView) ((ObjectAnimator) animation).getTarget());
                                    }
                                });

                                anim.setInterpolator(new DecelerateInterpolator());
                                anim.setRepeatCount(1);
                                anim.start();*/
                            ballLayout.addView(c);
                            ballCountMsg = new Message();
                            ballCountMsg.what = 1;
                            ballCountMsg.obj = myCircleList.size();
                            ballCountHandler.sendMessage(ballCountMsg);
                        }
                    }
                }

                animInterval++;

                    /*
                    //测试信号数据
                    state = "";
                    for (Map.Entry<String, Integer> m : map.entrySet()) {
                        sb.append(String.format("%s:%s ", m.getKey(), m.getValue()));
                    }

                    bid = intent.getStringExtra("bid");
                    beacon b = myApp.beaconList.get(bid);
                    if (b != null) {
                        state = String.format("您已进入展品%s区域", myApp.beaconList.get(bid).getDisplayname());
                    }

                    TextView txtView = (TextView) findViewById(R.id.txtView);
                    txtView.setText(sb.append(state).toString().replaceAll("\\s", "\n"));*/

                    /*
                    if (!intent.getExtras().getBoolean("playEnd")) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            //Toast.makeText(MainActivity.this, "Stop MainActivity Media", Toast.LENGTH_LONG).show();
                        }
                    }*/

                if (!myApp.headSetConnected) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        Toast.makeText(MainActivity.this, getString(R.string.msg_conn_headset), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//        }
//    }

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

    public class DlgEvent {
        public String beaconid;
        public int mode;//0->手动,1->自动

        public DlgEvent(String beaconid, int mode) {
            this.beaconid = beaconid;
            this.mode = mode;
        }
    }
}
