package com.buzz.unity;

import android.app.Service;
import android.content.*;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.*;

/**
 * Created by NickChung on 21/01/2015.
 */
public class BackgroundService extends Service {

    private static final String TAG = BackgroundService.class.getSimpleName();

    public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
    public static final String EXTRAS_BEACON = "extrasBeacon";

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    private BeaconManager beaconManager;
    private Beacon beacon;
    private Region region;

    private MyApplication myApp;
    private Map<String, List<Integer>> dic;
    int total;

    @Override
    public void onCreate() {
        super.onCreate();

        myApp = (MyApplication) getApplication();

        dic = new HashMap<String, List<Integer>>();

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> rangedBeacons) {
                for (Beacon b : rangedBeacons) {
                    String uuid = String.format("%s-%s", b.getMajor(), b.getMinor());
                    if (!dic.containsKey(uuid)) {
                        List<Integer> rawList = new ArrayList<Integer>();
                        rawList.add(b.getRssi());
                        dic.put(uuid, rawList);
                    } else {
                        List<Integer> rawList = dic.get(uuid);
                        rawList.add(b.getRssi());
                    }
                }

                Map<String, Integer> seqMap = new HashMap<String, Integer>();
                for (Map.Entry<String, List<Integer>> entry : dic.entrySet()) {
                    total = 0;
                    List<Integer> rawList = entry.getValue();

                    if (rawList.size() > 6) {
                        List<Integer> rawSubList = rawList.subList(rawList.size() - (6 + 1), rawList.size() - 1);
                        for (int r : rawSubList) {
                            total += r;
                        }

                        int avgRssi = (int) (total / 6);
                        seqMap.put(entry.getKey(), avgRssi);
                    }

                    String uid = "2397-3";
                    if (seqMap.containsKey(uid)) {
                        if (seqMap.get(uid) >= myApp.effectiveRange) {
                            if (!myApp.beaconState.containsKey(uid)) {
                                myApp.beaconState.put(uid, "01");
                                try {
                                    myApp.socketHelper.Send(String.format("%s %s,%s", "01", uid, myApp.appId));
                                } catch (Exception e) {
                                }
                            } else {
                                if (myApp.beaconState.get(uid) != "01") {
                                    myApp.beaconState.put(uid, "01");
                                    try {
                                        myApp.socketHelper.Send(String.format("%s %s,%s", "01", uid, myApp.appId));
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }

                        if (seqMap.get(uid) < myApp.effectiveRange) {
                            if (myApp.beaconState.containsKey(uid)) {
                                if (myApp.beaconState.get(uid) != "02") {
                                    myApp.beaconState.put(uid, "02");
                                    try {
                                        myApp.socketHelper.Send(String.format("%s %s,%s", "02", uid, myApp.appId));
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!beaconManager.hasBluetooth()) {
            //Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
        }

        if (!beaconManager.isBluetoothEnabled()) {
            //Toast.makeText(this, "Bluetooth does not Enabled", Toast.LENGTH_LONG).show();
        } else {
            connectToService();
        }
        return super.onStartCommand(intent, flags, startId);
    }


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
    }

    @Override
    public void onDestroy() {
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
        } catch (RemoteException e) {
            Log.d(TAG, "Error while stopping ranging", e);
        }

        beaconManager.disconnect();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
