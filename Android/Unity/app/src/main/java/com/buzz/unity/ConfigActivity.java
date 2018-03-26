package com.buzz.unity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigActivity extends ActionBarActivity {

    MyApplication myApp;
    private static final int REQUEST_ENABLE_BT = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        myApp = (MyApplication) getApplication();

        Button btnConfigOK = (Button) findViewById(R.id.btnConfigOK);
        final EditText etHost = (EditText) findViewById(R.id.etHost);
        final EditText etPort = (EditText) findViewById(R.id.etPort);

        etHost.setText(myApp.host);
        etPort.setText("1111");

        btnConfigOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myApp.checkWifi();
                myApp.checkBluetooth();

                if (!myApp.wifiReady) {
                    Toast.makeText(getApplicationContext(), "WIFI没有连接！", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!myApp.btReady) {
                    Toast.makeText(getApplicationContext(), "蓝牙没有打开！", Toast.LENGTH_LONG).show();
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    return;
                }

                myApp.setStrictMode();

                myApp.server = etHost.getText().toString();
                myApp.port = Integer.parseInt(etPort.getText().toString());
                myApp.connectServer(myApp.server, myApp.port);

                if (myApp.serverReady) {
                    Intent it = new Intent(ConfigActivity.this, MainActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "服务器异常！", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnConfigOK.callOnClick();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config, menu);
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
}
