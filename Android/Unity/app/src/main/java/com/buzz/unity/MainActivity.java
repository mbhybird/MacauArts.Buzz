package com.buzz.unity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    MyApplication myApp;

    private SensorManager sensorManager;
    private Vibrator vibrator;

    private static final int SENSOR_SHAKE = 10;

    ServerReceiver serverReceiver;
    Socket unitySocket;

    Thread mainThread;
    String info = "";
    Handler msgHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buzz);

        serverReceiver = new ServerReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SERVER");
        registerReceiver(serverReceiver, intentFilter);

        myApp = (MyApplication) getApplication();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        final TextView txtGameTitle = (TextView) findViewById(R.id.txtGameTitle);
        final TextView txtAppId = (TextView) findViewById(R.id.txtAppId);
        txtAppId.setText("用户编号：" + myApp.appId);

        final Button btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myApp.handlerId = myApp.appId;
                myApp.socketHelper.Send("03 01,Card52," + myApp.appId);
            }
        });

        mainThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }

                    if (!myApp.gameId.isEmpty()) {
                        info = "当前游戏：" + myApp.gameId;
                        if (myApp.rangeIn) {
                            if (myApp.gameId.equals("Pick a Card")) {
                                if (!myApp.player.isEmpty()) {
                                    if (myApp.player.equals(myApp.appId)) {
                                        info += "#(游戏进行中...)";
                                        vibrator.vibrate(200);
                                    } else {
                                        info += "#(玩家\"" + myApp.player + "\"游戏中...)";
                                    }
                                } else {
                                    info += "#(空闲...)";
                                }
                            } else if (myApp.gameId.equals("Slot Game")) {
                                if (!myApp.player.isEmpty()) {
                                    if (myApp.player.equals(myApp.appId)) {
                                        if (!myApp.slotSend) {
                                            try {
                                                Thread.sleep(500);
                                            } catch (Exception e) {
                                            }
                                            sendSlotCmd();
                                        }
                                    } else {
                                        info += "#(玩家\"" + myApp.player + "\"游戏中...)";
                                    }
                                } else {
                                    info += "#(空闲...)";
                                }
                            }
                        }

                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = info.replaceAll("#", "\n");
                        msgHandler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = "游戏未开始";
                        msgHandler.sendMessage(msg);
                    }

                    if (myApp.jsonServer.getServer().equals("01") && myApp.rangeIn) {
                        if (myApp.gameId.equals("52 Strong")) {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = true;
                            msgHandler.sendMessage(msg);
                        } else if (myApp.gameId.equals("Slot Game")) {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = false;
                            msgHandler.sendMessage(msg);
                        }
                    } else {
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = false;
                        msgHandler.sendMessage(msg);
                    }
                }
            }
        });

        msgHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        txtGameTitle.setText(msg.obj.toString());
                        break;

                    case 2:
                        if((boolean)msg.obj) {
                            btnPlay.setVisibility(View.VISIBLE);
                        }
                        else{
                            btnPlay.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
            }
        };

        mainThread.start();
    }

    private void sendSlotCmd() {
        myApp.handlerId = myApp.appId;
        myApp.socketHelper.Send("03 01,TigerGame," + myApp.appId);
        myApp.slotSend = true;
        myApp.back = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        try {
            unitySocket = new Socket(myApp.host, 2222);
        } catch (Exception e) {
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_card5_open:
                if (unitySocket != null && unitySocket.isConnected()) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(unitySocket.getOutputStream()));
                        writer.write("{\"gameid\":\"CardFive\",\"game\":\"01\"}");
                        writer.flush();
                    } catch (Exception e) {
                    }
                }
                break;
            case R.id.action_card52_open:
                if (unitySocket != null && unitySocket.isConnected()) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(unitySocket.getOutputStream()));
                        writer.write("{\"gameid\":\"Card52\",\"game\":\"01\"}");
                        writer.flush();
                    } catch (Exception e) {
                    }
                }
                break;

            case R.id.action_slot_open:
                if (unitySocket != null && unitySocket.isConnected()) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(unitySocket.getOutputStream()));
                        writer.write("{\"gameid\":\"TigerGame\",\"game\":\"01\"}");
                        writer.flush();
                    } catch (Exception e) {
                    }
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, BackgroundService.class);
        stopService(intent);
        unregisterReceiver(serverReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {// 注册监听器
            sensorManager.registerListener(sensorEventListener
                    , sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            return true;//返回true，把事件消费掉，不会继续调用onBackPressed
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 重力感应监听
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            //Log.i(TAG, "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度" + z);
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int mediumValue = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
            if (Math.abs(x) > mediumValue || Math.abs(y) > mediumValue || Math.abs(z) > mediumValue) {
                //vibrator.vibrate(200);
                Message msg = new Message();
                msg.what = SENSOR_SHAKE;
                //handler.sendMessage(msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    /**
     * 动作执行
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SENSOR_SHAKE:
                    Log.i(TAG, "检测到摇晃，执行操作！");
                    break;
            }
        }

    };

    public class ServerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("handlerId").equals(myApp.appId)) {
                myApp.handlerId = "";
                if (intent.getStringExtra("server").equals("01")) {
                    if (myApp.gameId.equals("52 Strong")) {
                        startActivity(new Intent(MainActivity.this, ShakeActivity.class));
                    } else if (myApp.gameId.equals("Slot Game")) {
                        startActivity(new Intent(MainActivity.this, SlotActivity.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "游戏未开始", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
