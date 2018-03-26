package com.buzz.unity;

import android.content.Intent;
import android.os.*;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.regex.*;

/**
 * Created by NickChung on 3/17/15.
 */
public class SocketHelper {

    final String TAG = this.getClass().getSimpleName();
    Socket socket;
    Thread socketMsgThread;
    Handler inHandler;
    InputStream in;
    String cmdType;
    MyApplication myApp;

    public SocketHelper(Socket socket, final MyApplication myApp) {
        this.socket = socket;
        this.myApp = myApp;
        inHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        String receiveData = msg.obj.toString();

                        if (receiveData.equals("{\"gameid\":\"CardFive\",\"game\":\"01\"}")) {
                            myApp.beaconState.clear();
                            myApp.jsonServer.setServer("02");
                            myApp.gameId = "Pick a Card";
                            myApp.player = "";
                        }

                        if (receiveData.equals("{\"gameid\":\"TigerGame\",\"game\":\"01\"}")) {
                            myApp.beaconState.clear();
                            myApp.jsonServer.setServer("01");
                            myApp.gameId = "Slot Game";
                            myApp.player = "";
                            myApp.slotSend = false;
                        }

                        if (receiveData.equals("{\"gameid\":\"Card52\",\"game\":\"01\"}")) {
                            myApp.beaconState.clear();
                            myApp.jsonServer.setServer("01");
                            myApp.gameId = "52 Strong";
                            myApp.player = "";
                            myApp.card52Send = false;
                        }

                        if (receiveData.contains("\"game\":\"02\"")) {
                            //myApp.gameId = "";
                            myApp.player = "";
                        }

                        if (receiveData.contains("playing")) {
                            String regex = "[{]\"playing\":\"(?i)(U[0-9]+)\"[}]";
                            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                            Matcher m = p.matcher(receiveData);
                            myApp.player = m.find() ? m.group(1) : "";
                        }

                        if (receiveData.contains("gameid") || receiveData.contains("playing")) {
                            cmdType = "08";
                        }

                        switch (cmdType) {
                            case "01":
                            case "02":
                                myApp.data = msg.obj.toString();
                                if (!myApp.data.isEmpty()) {
                                    if (myApp.data.contains("beaconid")) {
                                        if (myApp.data.contains("\"state\":\"01\"")) {
                                            myApp.rangeIn = true;
                                        } else {
                                            myApp.rangeIn = false;
                                        }
                                    }
                                }
                                break;

                            case "03":
                                try {
                                    JsonServerModel server = myApp.objectMapper.readValue(msg.obj.toString(), JsonServerModel.class);
                                    if (server != null) {
                                        if (server.getUserid().equals(myApp.appId)) {
                                            myApp.jsonServer.setColor(server.getColor());
                                            myApp.color = server.getColor();
                                            myApp.jsonServer.setServer(server.getServer());
                                        }
                                        if (myApp.userId.equals("")) {
                                            myApp.userId = server.getUserid();
                                            myApp.jsonServer.setUserid(server.getUserid());
                                        }

                                        Intent it = new Intent("android.intent.action.SERVER");
                                        it.putExtra("server", myApp.jsonServer.getServer());
                                        it.putExtra("handlerId", myApp.handlerId);
                                        myApp.sendBroadcast(it);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;

                            case "04":
                            case "05":
                            case "06":
                                try {
                                    JsonUserModel user = myApp.objectMapper.readValue(msg.obj.toString(), JsonUserModel.class);
                                    if (user != null) {
                                        if (user.getUserid().equals(myApp.userId)) {
                                            myApp.jsonUser.setState(user.getState());
                                            if (!user.getValue().equals("")) {
                                                myApp.jsonUser.setValue(user.getValue());
                                            }
                                        }
                                    }
                                } catch (Exception e) {

                                }
                                break;

                            case "07":
                                try {
                                    JsonUserModel[] users = myApp.objectMapper.readValue(msg.obj.toString(), JsonUserModel[].class);
                                    if (users != null) {
                                        for (JsonUserModel user : users) {
                                            if (user.getUserid().equals(myApp.userId)) {
                                                myApp.jsonUser.setState(user.getState());
                                                if (!user.getValue().equals("")) {
                                                    myApp.jsonUser.setValue(user.getValue());
                                                }

                                                Intent it = new Intent("android.intent.action.CARD52_OPEN");
                                                myApp.sendBroadcast(it);
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                                break;

                            case "08":
                                break;

                        }
                        //Log.i(TAG, msg.obj.toString());
                }
            }
        };

        try {
            in = socket.getInputStream();
        } catch (Exception e) {
            System.out.print(e.toString());
            return;
        }
        socketMsgThread = new Thread(runnable);
        socketMsgThread.start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    byte[] temp = new byte[256];
                    int size = 0;
                    while ((size = in.read(temp)) > 0) {
                        // -1表示文件结尾
                        byte[] res = new byte[size];
                        System.arraycopy(temp, 0, res, 0, size);
                        Message message = new Message();
                        message.what = 1;
                        message.obj = new String(res, "utf-8");
                        String objStr = message.obj.toString();
                        if (objStr.startsWith("[") && objStr.endsWith("]")) {
                            cmdType = "07";
                        }
                        inHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    System.out.print(e.toString());
                    return;
                }
            }
        }
    };

    public void Send(String commandArgs) {
        cmdType = commandArgs.substring(0, 2);
        try {
            if (socket != null && socket.isConnected()) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write(commandArgs);
                writer.flush();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.print(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print(e.toString());
        }
    }
}
