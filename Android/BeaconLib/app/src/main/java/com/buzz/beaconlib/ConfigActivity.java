package com.buzz.beaconlib;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;


public class ConfigActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Button btnConfigOK = (Button) findViewById(R.id.btnConfigOK);
        EditText etHost = (EditText) findViewById(R.id.etHost);
        EditText etPort = (EditText) findViewById(R.id.etPort);

        etHost.setText("192.168.0.106");
        etPort.setText("2397");

        btnConfigOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ConfigActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                EditText host = (EditText) findViewById(R.id.etHost);
                EditText port = (EditText) findViewById(R.id.etPort);
                bundle.putString("HOST", host.getText().toString());
                bundle.putString("PORT", port.getText().toString());
                it.putExtras(bundle);
                startActivity(it);
            }
        });
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
