package com.buzz.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.*;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.buzz.activity.*;
import com.buzz.models.*;
import com.buzz.receiver.HeadSetUtil;
import com.buzz.utils.*;
import com.buzz.receiver.AppMonitorReceiver;

//import com.estimote.sdk.Beacon;
//import com.estimote.sdk.BeaconManager;
//import com.estimote.sdk.Region;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by NickChung on 21/01/2015.
 */
public class MessageService extends Service {

    private static final String TAG = MessageService.class.getSimpleName();

    /*
    public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
    public static final String EXTRAS_BEACON = "extrasBeacon";

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    private BeaconManager beaconManager;
    private Beacon beacon;
    private Region region;

    private Map<String, List<Integer>> dic;

    private int total;
    */

    private MediaPlayer mediaPlayer;
    private String lastPlay = "";
    private String currentPlay = "";
    private Boolean isPlayEnd = true;
    private String state = "";

    private NotificationManager manager;
    private MyApplication myApp;
    private HeadsetPlugReceiver headsetPlugReceiver;
    private AppMonitorReceiver appMonitorReceiver;
    private BeaconReceiver myBeaconReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        registerHeadsetPlugReceiver();
        registerAppMonitorReceiver();
        registerBeaconReceiver();

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /*
        dic = new HashMap<String, List<Integer>>();

        beaconManager = new BeaconManager(this);
        beaconManager.setBackgroundScanPeriod(GlobalConst.BACKGROUND_SCAN_PERIOD, 0);
        beaconManager.setForegroundScanPeriod(GlobalConst.FOREGROUND_SCAN_PERIOD, 0);

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> rangedBeacons) {
                if (myApp.stopServiceMedia) {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        isPlayEnd = true;
                        myApp.stopServiceMedia = false;
                    }

                    myApp.stopServiceMedia = false;
                }

                for (Beacon beacon : rangedBeacons) {
                    int minor = beacon.getMinor();
                    int major = beacon.getMajor();
                    String uniqueKey = String.format("%s-%s", major, minor);

                    if (!dic.containsKey(uniqueKey)) {
                        List<Integer> rawList = new ArrayList<Integer>();
                        rawList.add(beacon.getRssi());
                        dic.put(uniqueKey, rawList);
                    } else {
                        List<Integer> rawList = dic.get(uniqueKey);
                        rawList.add(beacon.getRssi());
                    }
                }

                Map<String, Integer> seqMap = new HashMap<String, Integer>();
                for (Map.Entry<String, List<Integer>> entry : dic.entrySet()) {
                    total = 0;
                    List<Integer> rawList = entry.getValue();

                    if (rawList.size() > GlobalConst.RSSI_SMOOTH_STEP) {
                        List<Integer> rawSubList = rawList.subList(rawList.size() - (GlobalConst.RSSI_SMOOTH_STEP + 1), rawList.size() - 1);
                        for (int r : rawSubList) {
                            total += r;
                        }

                        int avgRssi = (int) (total / GlobalConst.RSSI_SMOOTH_STEP);
                        seqMap.put(entry.getKey(), avgRssi);
                        beacon b = myApp.beaconList.get(entry.getKey());

                        int rangeIn = -1;
                        if (b != null) {
                            //获取当前信号状态，前台或后台或所有
                            if (myApp.appRunningState.equals(GlobalConst.AppRunningState.FRONT)) {
                                //如果是前台读取，则取前台对应的Beacon响应值
                                if (b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_FRONT)
                                        || b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                                    rangeIn = Integer.parseInt(b.getEffectiverangein());
                                }
                            } else if (myApp.appRunningState.equals(GlobalConst.AppRunningState.BACK)) {
                                //如果是后台读取，则取后台对应的Beacon响应值
                                if (b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BACK)
                                        || b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                                    rangeIn = Integer.parseInt(b.getEffectiverangein());
                                }
                            }
                        }

                        if (avgRssi >= rangeIn) {
                            currentPlay = entry.getKey();
                            state = "In";

                            //写日志，一进一出，配对出现
                            if (!myApp.beaconStateList.containsKey(currentPlay)) {
                                showNotification(currentPlay);
                                myApp.beaconStateList.put(currentPlay, GlobalConst.TRIGGER_TYPE_IN);
                                String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                //连接上服务器才写日志
                                if (myApp.serverReady) {
                                    myApp.socketHelper.Send(String.format(GlobalConst.CMD_ADD_SYS_LOG, myApp.logonUser.userId, currentPlay, dt, GlobalConst.TRIGGER_TYPE_IN));
                                }
                            } else {
                                if (myApp.beaconStateList.get(currentPlay) != GlobalConst.TRIGGER_TYPE_IN) {
                                    showNotification(currentPlay);
                                    myApp.beaconStateList.put(currentPlay, GlobalConst.TRIGGER_TYPE_IN);
                                    String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                    //连接上服务器才写日志
                                    if (myApp.serverReady) {
                                        myApp.socketHelper.Send(String.format(GlobalConst.CMD_ADD_SYS_LOG, myApp.logonUser.userId, currentPlay, dt, GlobalConst.TRIGGER_TYPE_IN));
                                    }
                                }
                            }

                        }

                        int rangeOut = -100;
                        if (b != null) {
                            //获取当前信号状态，前台或后台或所有
                            if (myApp.appRunningState.equals(GlobalConst.AppRunningState.FRONT)) {
                                //如果是前台读取，则取前台对应的Beacon响应值
                                if (b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_FRONT)
                                        || b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                                    rangeOut = Integer.parseInt(b.getEffectiverangeout());
                                }
                            } else if (myApp.appRunningState.equals(GlobalConst.AppRunningState.BACK)) {
                                //如果是后台读取，则取后台对应的Beacon响应值
                                if (b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BACK)
                                        || b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                                    rangeOut = Integer.parseInt(b.getEffectiverangeout());
                                }
                            }
                        }

                        //写日志，一进一出，配对出现
                        if (avgRssi < rangeOut) {
                            if (myApp.beaconStateList.containsKey(entry.getKey())) {
                                if (myApp.beaconStateList.get(entry.getKey()) != GlobalConst.TRIGGER_TYPE_OUT) {
                                    myApp.beaconStateList.put(entry.getKey(), GlobalConst.TRIGGER_TYPE_OUT);
                                    String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                    //连接上服务器才写日志
                                    if (myApp.serverReady) {
                                        myApp.socketHelper.Send(String.format(GlobalConst.CMD_ADD_SYS_LOG, myApp.logonUser.userId, entry.getKey(), dt, GlobalConst.TRIGGER_TYPE_OUT));
                                    }
                                }
                            }
                        }
                        //Log.i(TAG, String.format("%s:%s", entry.getKey(), avgRssi));
                    }
                }

                //最后响应Beacon
                if (seqMap.containsKey(lastPlay)) {
                    int rangeOut = -100;
                    if (myApp.appRunningState.equals(GlobalConst.AppRunningState.FRONT)) {
                        //如果是前台读取，则取前台对应的Beacon响应值
                        if (myApp.beaconList.get(lastPlay).getRangedirection().equals(GlobalConst.RANGE_DIRECTION_FRONT)
                                || myApp.beaconList.get(lastPlay).getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                            rangeOut = Integer.parseInt(myApp.beaconList.get(lastPlay).getEffectiverangeout());
                        }
                    } else if (myApp.appRunningState.equals(GlobalConst.AppRunningState.BACK)) {
                        //如果是后台读取，则取后台对应的Beacon响应值
                        if (myApp.beaconList.get(lastPlay).getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BACK)
                                || myApp.beaconList.get(lastPlay).getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                            rangeOut = Integer.parseInt(myApp.beaconList.get(lastPlay).getEffectiverangeout());
                        }
                    }

                    //如果超出范围则停止播放音频
                    if (seqMap.get(lastPlay).intValue() < rangeOut) {
                        state = "Out";
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                            isPlayEnd = true;
                        }
                    }
                }

                //设置广播值返回给前台Activity
                Intent intent = new Intent(GlobalConst.ACTION_MY_APP_SERVICE);
                intent.putExtra("map", (Serializable) seqMap);
                intent.putExtra("bid", currentPlay);
                intent.putExtra("state", state);

                if (currentPlay != "") {
                    if (!myApp.playHistoryList.containsKey(currentPlay)) {
                        myApp.playHistoryList.put(currentPlay, new PlayHistory());
                        if (currentPlay != lastPlay) {
                            if (mediaPlayer != null) {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                                isPlayEnd = true;
                            }
                        }
                        if (isPlayEnd) {
                            //showNotification(currentPlay);
                            mediaPlayer = new MediaPlayer();
                            lastPlay = currentPlay;
                            currentPlay = "";
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    // TODO Auto-generated method stub
                                    mediaPlayer.release();
                                    mediaPlayer = null;
                                    isPlayEnd = true;
                                }
                            });


                            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                                @Override
                                public boolean onError(MediaPlayer mp, int what, int extra) {
                                    // TODO Auto-generated method stub
                                    mediaPlayer.release();
                                    mediaPlayer = null;
                                    isPlayEnd = true;
                                    return true;
                                }
                            });

                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    if (myApp.headSetConnected) {
                                        mediaPlayer.start();
                                        isPlayEnd = false;
                                    }
                                }
                            });

                            try {

                                String audioPath = "";
                                String imagePath = "";
                                String imageDesc = "";

                                Iterator it = myApp.actionList.get(lastPlay).iterator();

                                while (it.hasNext()) {
                                    action ac = (action) it.next();
                                    if (ac.getContenttype().equals("image")) {
                                        imagePath = GlobalConst.PATH_SDCARD.concat(ac.getClientpath()).concat(ac.getFilename());
                                        imageDesc = ac.getDescription_cn();
                                    } else if (ac.getContenttype().equals("audio")) {
                                        audioPath = GlobalConst.PATH_SDCARD.concat(ac.getClientpath()).concat(ac.getFilename());
                                    }
                                }

                                myApp.playHistoryList.get(lastPlay).audioPath = audioPath;
                                myApp.playHistoryList.get(lastPlay).imagePath = imagePath;
                                myApp.playHistoryList.get(lastPlay).imageDesc = imageDesc;

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
                                mediaPlayer.reset();
                                mediaPlayer.setDataSource(audioPath.replace(".mp3", suffix));
                                mediaPlayer.prepare();

                            } catch (Exception ex) {
                                Log.i(TAG, ex.toString());
                            }
                        }
                    }
                }

                intent.putExtra("playEnd", isPlayEnd);
                sendBroadcast(intent);
            }
        });*/

        //注册耳机事件
        HeadSetUtil.getInstance().setOnHeadSetListener(new HeadSetUtil.OnHeadSetListener() {
            @Override
            public void onClick() {
                Intent it = new Intent(GlobalConst.ACTION_STOP_MEDIA);
                it.putExtra("action", "sc");
                sendBroadcast(it);
            }

            @Override
            public void onDoubleClick() {
                Intent it = new Intent(GlobalConst.ACTION_STOP_MEDIA);
                it.putExtra("action", "dc");
                sendBroadcast(it);
            }

            @Override
            public void onTripleClick() {
                Intent it = new Intent(GlobalConst.ACTION_STOP_MEDIA);
                it.putExtra("action", "tc");
                sendBroadcast(it);
            }
        });

        HeadSetUtil.getInstance().open(this);
    }

    private void showNotification(String beaconid) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        // Adds the back stack
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent to the top of the stack
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("bid", beaconid);
        resultIntent.putExtras(bundle);
        stackBuilder.addNextIntent(resultIntent);
        // Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Iterator<content> it = myApp.actionList.get(beaconid).iterator();

        String title = "";
        while (it.hasNext()) {
            content c = it.next();
            if (c.getContenttype() == GlobalConst.CONTENT_TYPE_IMAGE) {
                switch (myApp.logonUser.defaultLang) {
                    case GlobalConst.DEFAULT_LANG_EN:
                        title = c.getTitle_en() + "-" + c.getArtist_en();
                        break;
                    case GlobalConst.DEFAULT_LANG_CN:
                        title = c.getTitle_cn() + "-" + c.getArtist_cn();
                        break;
                    case GlobalConst.DEFAULT_LANG_TW:
                        title = c.getTitle_tw() + "-" + c.getArtist_tw();
                        break;
                    case GlobalConst.DEFAULT_LANG_PT:
                        title = c.getTitle_pt() + "-" + c.getArtist_pt();
                        break;
                }
            }
        }

        Notification notification = new Notification.Builder(getApplicationContext())
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_logo))
                .setLargeIcon(ImageHelper.readBitMap(getApplicationContext(), R.drawable.icon_logo))
                .setSmallIcon(R.drawable.icon_logo)
                .setTicker(getString(R.string.app_name))
                .setContentInfo("->")
                .setContentTitle(getString(R.string.msg_dlg_title_ex_tag))
                .setContentText(title)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .build();
        manager.notify(0, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        myApp = (MyApplication) getApplication();
        //myApp.initSettings();

        /*
        if (!beaconManager.hasBluetooth()) {
            //Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
        }

        if (!beaconManager.isBluetoothEnabled()) {
            //Toast.makeText(this, "Bluetooth does not Enabled", Toast.LENGTH_LONG).show();
        } else {
            connectToService();
        }*/
        return super.onStartCommand(intent, flags, startId);
    }


    /*
    private void connectToService() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                } catch (RemoteException e) {
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
    }*/

    @Override
    public void onDestroy() {
        /*
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
        } catch (RemoteException e) {
            Log.d(TAG, "Error while stopping ranging", e);
        }

        beaconManager.disconnect();*/
        super.onDestroy();

        unregisterReceiver(headsetPlugReceiver);
        unregisterReceiver(appMonitorReceiver);
        unregisterReceiver(myBeaconReceiver);

        HeadSetUtil.getInstance().close(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private void registerAppMonitorReceiver() {
        appMonitorReceiver = new AppMonitorReceiver();
        IntentFilter filter = new IntentFilter(GlobalConst.ACTION_APP_RUNNING_MONITOR);
        registerReceiver(appMonitorReceiver, filter);
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
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        isPlayEnd = true;
                    }
                } else if (intent.getIntExtra("state", 0) == 1) {
                    Log.i(TAG, "headset connected");
                    myApp.headSetConnected = true;
                }
            }
        }
    }

    private void registerBeaconReceiver() {
        myBeaconReceiver = new MyBeaconReceiver();
        IntentFilter filter = new IntentFilter(CoreService.ACTION_BEACON_SEND);
        registerReceiver(myBeaconReceiver, filter);
    }

    private class MyBeaconReceiver extends BeaconReceiver {

        @Override
        public void Do(Map<String, BeaconReader> map) {
            if (myApp.stopServiceMedia) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    isPlayEnd = true;
                    myApp.stopServiceMedia = false;
                }

                myApp.stopServiceMedia = false;
            }

            for (BeaconReader beaconReader : map.values()) {
                beacon b = myApp.beaconList.get(beaconReader.getBeaconId());

                int rangeIn = -1;
                if (b != null) {
                    //获取当前信号状态，前台或后台或所有
                    if (myApp.appRunningState.equals(GlobalConst.AppRunningState.FRONT)) {
                        //如果是前台读取，则取前台对应的Beacon响应值
                        if (b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_FRONT)
                                || b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                            rangeIn = b.getEffectiverangein();
                        }
                    } else if (myApp.appRunningState.equals(GlobalConst.AppRunningState.BACK)) {
                        //如果是后台读取，则取后台对应的Beacon响应值
                        if (b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BACK)
                                || b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                            rangeIn = b.getEffectiverangein();
                        }
                    }
                }

                if (beaconReader.getRssi() >= rangeIn) {
                    currentPlay = beaconReader.getBeaconId();
                    state = "In";

                    //写日志，一进一出，配对出现
                    if (!myApp.beaconStateList.containsKey(currentPlay)) {
                        showNotification(currentPlay);
                        myApp.beaconStateList.put(currentPlay, GlobalConst.TRIGGER_TYPE_IN);
                        String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        //连接上服务器才写日志
                        if (myApp.serverReady) {
                            try {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("userid", myApp.logonUser.userId);
                                jsonObject.put("beaconid", currentPlay);
                                jsonObject.put("logtime", dt);
                                jsonObject.put("triggertype", GlobalConst.TRIGGER_TYPE_IN);
                                jsonObject.put("extag", myApp.beaconList.get(currentPlay).getExtag());
                                ConfigHelper.getInstance(getApplicationContext()).addSysLog(jsonObject);
                            } catch (JSONException e) {
                            }
                            //myApp.socketHelper.Send(String.format(GlobalConst.CMD_ADD_SYS_LOG, myApp.logonUser.userId, currentPlay, dt, GlobalConst.TRIGGER_TYPE_IN));
                        }
                    } else {
                        if (myApp.beaconStateList.get(currentPlay) != GlobalConst.TRIGGER_TYPE_IN) {
                            showNotification(currentPlay);
                            myApp.beaconStateList.put(currentPlay, GlobalConst.TRIGGER_TYPE_IN);
                            String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            //连接上服务器才写日志
                            if (myApp.serverReady) {
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("userid", myApp.logonUser.userId);
                                    jsonObject.put("beaconid", currentPlay);
                                    jsonObject.put("logtime", dt);
                                    jsonObject.put("triggertype", GlobalConst.TRIGGER_TYPE_IN);
                                    jsonObject.put("extag", myApp.beaconList.get(currentPlay).getExtag());
                                    ConfigHelper.getInstance(getApplicationContext()).addSysLog(jsonObject);
                                } catch (JSONException e) {
                                }
                                //myApp.socketHelper.Send(String.format(GlobalConst.CMD_ADD_SYS_LOG, myApp.logonUser.userId, currentPlay, dt, GlobalConst.TRIGGER_TYPE_IN));
                            }
                        }
                    }

                }

                int rangeOut = -100;
                if (b != null) {
                    //获取当前信号状态，前台或后台或所有
                    if (myApp.appRunningState.equals(GlobalConst.AppRunningState.FRONT)) {
                        //如果是前台读取，则取前台对应的Beacon响应值
                        if (b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_FRONT)
                                || b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                            rangeOut = b.getEffectiverangeout();
                        }
                    } else if (myApp.appRunningState.equals(GlobalConst.AppRunningState.BACK)) {
                        //如果是后台读取，则取后台对应的Beacon响应值
                        if (b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BACK)
                                || b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                            rangeOut = b.getEffectiverangeout();
                        }
                    }
                }

                //写日志，一进一出，配对出现
                if (beaconReader.getRssi() < rangeOut) {
                    if (myApp.beaconStateList.containsKey(beaconReader.getBeaconId())) {
                        if (myApp.beaconStateList.get(beaconReader.getBeaconId()) != GlobalConst.TRIGGER_TYPE_OUT) {
                            myApp.beaconStateList.put(beaconReader.getBeaconId(), GlobalConst.TRIGGER_TYPE_OUT);
                            String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            //连接上服务器才写日志
                            if (myApp.serverReady) {
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("userid", myApp.logonUser.userId);
                                    jsonObject.put("beaconid", beaconReader.getBeaconId());
                                    jsonObject.put("logtime", dt);
                                    jsonObject.put("triggertype", GlobalConst.TRIGGER_TYPE_OUT);
                                    jsonObject.put("extag", myApp.beaconList.get(beaconReader.getBeaconId()).getExtag());
                                    ConfigHelper.getInstance(getApplicationContext()).addSysLog(jsonObject);
                                } catch (JSONException e) {
                                }
                                //myApp.socketHelper.Send(String.format(GlobalConst.CMD_ADD_SYS_LOG, myApp.logonUser.userId, beaconReader.getBeaconId(), dt, GlobalConst.TRIGGER_TYPE_OUT));
                            }
                        }
                    }
                }
            }

            //最后响应Beacon
            if (map.containsKey(lastPlay)) {
                int rangeOut = -100;
                beacon b = myApp.beaconList.get(lastPlay);
                if (b != null) {
                    if (myApp.appRunningState.equals(GlobalConst.AppRunningState.FRONT)) {
                        //如果是前台读取，则取前台对应的Beacon响应值
                        if (b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_FRONT)
                                || b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                            rangeOut = b.getEffectiverangeout();
                        }
                    } else if (myApp.appRunningState.equals(GlobalConst.AppRunningState.BACK)) {
                        //如果是后台读取，则取后台对应的Beacon响应值
                        if (b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BACK)
                                || b.getRangedirection().equals(GlobalConst.RANGE_DIRECTION_BOTH)) {
                            rangeOut = b.getEffectiverangeout();
                        }
                    }
                }

                //如果超出范围则停止播放音频
                if (map.get(lastPlay).getRssi() < rangeOut) {
                    state = "Out";
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        isPlayEnd = true;
                    }
                }
            }

            /*
            //设置广播值返回给前台Activity
            Intent intent = new Intent(GlobalConst.ACTION_MY_APP_SERVICE);
            intent.putExtra("map", (Serializable) seqMap);
            intent.putExtra("bid", currentPlay);
            intent.putExtra("state", state);*/

            if (currentPlay != "") {
                if (!myApp.playHistoryList.containsKey(currentPlay)) {
                    myApp.playHistoryList.put(currentPlay, new PlayHistory());
                    if (currentPlay != lastPlay) {
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                            isPlayEnd = true;
                        }
                    }
                    if (isPlayEnd) {
                        //showNotification(currentPlay);
                        mediaPlayer = new MediaPlayer();
                        lastPlay = currentPlay;
                        currentPlay = "";
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                // TODO Auto-generated method stub
                                mediaPlayer.release();
                                mediaPlayer = null;
                                isPlayEnd = true;
                            }
                        });


                        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mp, int what, int extra) {
                                // TODO Auto-generated method stub
                                mediaPlayer.release();
                                mediaPlayer = null;
                                isPlayEnd = true;
                                return true;
                            }
                        });

                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                if (myApp.headSetConnected) {
                                    mediaPlayer.start();
                                    isPlayEnd = false;
                                }
                            }
                        });

                        try {

                            String audioPath = "";
                            String imagePath = "";
                            String imageDesc = "";

                            Iterator<content> it = myApp.actionList.get(lastPlay).iterator();

                            while (it.hasNext()) {
                                content c = it.next();
                                if (c.getContenttype() == GlobalConst.CONTENT_TYPE_IMAGE) {
                                    imagePath = GlobalConst.PATH_SDCARD.concat(c.getClientpath()).concat(c.getFilename());
                                    imageDesc = c.getDescription_cn();
                                } else if (c.getContenttype() == GlobalConst.CONTENT_TYPE_AUDIO) {
                                    audioPath = GlobalConst.PATH_SDCARD.concat(c.getClientpath()).concat(c.getFilename());
                                }
                            }

                            myApp.playHistoryList.get(lastPlay).audioPath = audioPath;
                            myApp.playHistoryList.get(lastPlay).imagePath = imagePath;
                            myApp.playHistoryList.get(lastPlay).imageDesc = imageDesc;

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
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(audioPath.replace(".mp3", suffix));
                            mediaPlayer.prepare();

                        } catch (Exception ex) {
                            Log.i(TAG, ex.toString());
                        }
                    }
                }
            }

            //intent.putExtra("playEnd", isPlayEnd);
            //sendBroadcast(intent);
        }
    }
}
