package com.buzz.unity;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.os.StrictMode;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by NickChung on 3/17/15.
 */
public class MyApplication extends Application {

    boolean wifiReady;
    boolean btReady;
    boolean serverReady;
    Socket socket;
    public String color;
    public String appId;
    public String handlerId;
    public SocketHelper socketHelper;
    public String data;
    public Boolean rangeIn;
    ObjectMapper objectMapper;
    JsonServerModel jsonServer;
    JsonUserModel jsonUser;
    public String server;
    public Integer port;
    public Map<String, String> beaconState;
    public String userId = "";
    public String gameId = "";
    public String player = "";
    public String host = "192.168.0.202";
    public int effectiveRange = -68;
    public boolean back = false;
    public boolean slotSend = false;
    public boolean card52Send = false;

    public MyApplication() {
        wifiReady = false;
        btReady = false;
        serverReady = false;
        data = new String();
        rangeIn = false;
        objectMapper = new ObjectMapper();
        jsonServer = new JsonServerModel();
        jsonUser = new JsonUserModel();
        appId = "U" + String.valueOf(new Random(System.currentTimeMillis()).nextInt(256));
        beaconState = new HashMap<String, String>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    public void setStrictMode() {
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
    }

    public void connectServer(String host, int port) {
        try {
            socket = new Socket(host, port);
            if (socket.isConnected()) {
                socketHelper = new SocketHelper(socket, this);
                serverReady = true;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.print(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print(e.toString());
        }
    }

    public void checkWifi() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
        this.wifiReady = wifi;
    }

    public void checkBluetooth() {
        boolean bt = BluetoothAdapter.getDefaultAdapter().isEnabled();
        this.btReady = bt;
    }

    /*
    public void genUID() {
        Random rdm = new Random(System.currentTimeMillis());
        this.userId = "UID" + String.valueOf(Math.abs(rdm.nextInt()) % 32 + 1);

    }

    public void genColor() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        this.color = r + g + b;
    }*/
}
