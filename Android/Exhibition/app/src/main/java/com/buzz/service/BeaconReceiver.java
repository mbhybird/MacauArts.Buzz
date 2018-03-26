package com.buzz.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.buzz.activity.MyApplication;

import java.util.Map;

/**
 * Created by NickChung on 7/20/15.
 */
public class BeaconReceiver extends BroadcastReceiver {

    final static String TAG = BeaconReader.class.getSimpleName();

    @Override
    final public void onReceive(Context context, Intent intent) {
        Map<String, BeaconReader> beaconReaderMap = (Map<String, BeaconReader>) intent.getSerializableExtra("map");

        MyApplication myApp = (MyApplication) context.getApplicationContext();
        myApp.readerMap = beaconReaderMap;

        Log.i(TAG, String.valueOf(beaconReaderMap.size()));
        for (Map.Entry<String, BeaconReader> entry : myApp.readerMap.entrySet()) {
            Log.i(TAG, entry.getKey() + "," + entry.getValue().getRssi());
        }
        Do(beaconReaderMap);
    }

    public void Do(Map<String, BeaconReader> map) {

    }
}
