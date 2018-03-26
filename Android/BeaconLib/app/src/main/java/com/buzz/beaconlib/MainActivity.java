package com.buzz.beaconlib;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.os.StrictMode;
import android.os.Handler;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Message;
import android.widget.Button;
import android.view.View;
//import android.util.Log;
import android.widget.ArrayAdapter;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.*;
import java.lang.*;
import java.util.*;


public class MainActivity extends ActionBarActivity {

    private static String HOST = "192.168.0.106";
    private static int PORT = 2397;
    static String ActiveCMD = "";

    Socket socket;
    Thread socketMsgThread;
    Handler inHandler;
    Handler resultHandler;
    InputStream in;
    ObjectMapper objectMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        HOST = bundle.getString("HOST");
        PORT = Integer.parseInt(bundle.getString("PORT"));

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

        final TextView tvHost = (TextView) findViewById(R.id.tvHost);
        tvHost.setText(HOST);

        final TextView tvPort = (TextView) findViewById(R.id.tvPort);
        tvPort.setText(String.valueOf(PORT));

        try {
            socket = new Socket(HOST, PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            ShowDialog(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            ShowDialog(e.getMessage());
        }

        final Button btnGetBeaconInfo = (Button) findViewById(R.id.btnGetBeaconInfo);
        final Button btnGetSysparams = (Button) findViewById(R.id.btnGetSysparams);
        final Button btnGetUserInfo = (Button) findViewById(R.id.btnGetUserInfo);
        final Button btnAddSyslog = (Button) findViewById(R.id.btnAddSyslog);
        final Button btnAddKey = (Button) findViewById(R.id.btnAddKey);
        final Button btnOpen = (Button) findViewById(R.id.btnOpen);
        final Button btnClose = (Button) findViewById(R.id.btnClose);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(GlobalConst.CMD_OPEN);
                    writer.flush();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                }

                ActiveCMD = "06";
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(GlobalConst.CMD_CLOSE);
                    writer.flush();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                }

                ActiveCMD = "07";
            }
        });

        btnGetBeaconInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(GlobalConst.CMD_GET_BEACON_INFO);
                    writer.flush();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                }

                ActiveCMD = "01";
            }
        });

        btnGetSysparams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(GlobalConst.CMD_GET_SYS_PARAMS);
                    writer.flush();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                }

                ActiveCMD = "02";
            }
        });


        btnGetUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(GlobalConst.CMD_GET_USER_INFO);
                    writer.flush();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                }

                ActiveCMD = "03";
            }
        });


        btnAddSyslog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(GlobalConst.CMD_ADD_SYS_LOG);
                    writer.flush();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                }

                ActiveCMD = "04";
            }
        });


        btnAddKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(GlobalConst.CMD_ADD_KEY);
                    writer.flush();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    ShowDialog(e.getMessage());
                }

                ActiveCMD = "05";
            }
        });


        objectMapper = new ObjectMapper();

        resultHandler = new Handler() {
            public void handleMessage(Message msg) {
                List<String> ds = new ArrayList<String>();
                switch (msg.what) {
                    case 1:
                        BeaconInfo beaconInfo = (BeaconInfo) msg.obj;
                        ds.add(beaconInfo.getBeacon().get(0).toString());
                        for (triggers t : beaconInfo.getTriggers()) {
                            ds.add(t.toString());
                        }
                        for (contents c : beaconInfo.getContents()) {
                            ds.add(c.toString());
                        }
                        ShowResult(ds);
                        break;

                    case 2:
                        Sysparams[] sysparamsList = (Sysparams[]) msg.obj;
                        for (Sysparams sp : sysparamsList) {
                            ds.add(sp.toString());
                        }
                        ShowResult(ds);
                        break;

                    case 3:
                        UserInfo[] userInfoList = (UserInfo[]) msg.obj;
                        ds.add(userInfoList[0].toString());
                        ShowResult(ds);
                        break;

                    case 4:
                        ShowResult(ds);
                        break;
                }
            }
        };

        inHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        TextView tvJsonData = (TextView) findViewById(R.id.tvJsonData);
                        tvJsonData.setText(msg.obj.toString());

                        switch (ActiveCMD) {
                            case "01":
                                try {
                                    BeaconInfo beaconInfo = objectMapper.readValue(msg.obj.toString(), BeaconInfo.class);
                                    Message message = new Message();
                                    message.what = 1;
                                    message.obj = beaconInfo;
                                    resultHandler.sendMessage(message);
                                } catch (IOException ex) {
                                    ShowDialog(ex.toString());
                                    return;
                                }
                                break;

                            case "02":
                                try {
                                    Sysparams[] SysparamsList = objectMapper.readValue(msg.obj.toString(), Sysparams[].class);
                                    Message message = new Message();
                                    message.what = 2;
                                    message.obj = SysparamsList;
                                    resultHandler.sendMessage(message);
                                } catch (IOException ex) {
                                    ShowDialog(ex.toString());
                                    return;
                                }
                                break;

                            case "03":
                                try {
                                    UserInfo[] userInfoList = objectMapper.readValue(msg.obj.toString(), UserInfo[].class);
                                    Message message = new Message();
                                    message.what = 3;
                                    message.obj = userInfoList;
                                    resultHandler.sendMessage(message);
                                } catch (IOException ex) {
                                    ShowDialog(ex.toString());
                                    return;
                                }
                                break;

                            default:
                                Message message = new Message();
                                message.what = 4;
                                resultHandler.sendMessage(message);
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        try {
            in = socket.getInputStream();
        } catch (Exception ex) {
            return;
        }
        socketMsgThread = new Thread(runnable);
        socketMsgThread.start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String line = "";
            while (true) {
                try {
                    byte[] temp = new byte[10240];// 默认最带为256
                    int size = 0;
                    while ((size = in.read(temp)) > 0) {
                        // -1表示文件结尾
                        byte[] res = new byte[size];// 默认最带为256
                        System.arraycopy(temp, 0, res, 0, size);
                        for (int i = 0; i < size; i++) {
                            line += (char) res[i];
                        }

                        Message message = new Message();
                        message.what = 1;
                        message.obj = line;
                        inHandler.sendMessage(message);
                        line = "";
                    }
                } catch (Exception ex) {
                    return;
                }
            }
        }
    };


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ShowResult(List<String> ds){
        ListView lvResult = (ListView) findViewById(R.id.lvResult);
        lvResult.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,ds));
    }

    private void ShowDialog(String msg) {
        new AlertDialog
            .Builder(this)
            .setTitle("notification")
            .setMessage(msg)
            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
    }
}
