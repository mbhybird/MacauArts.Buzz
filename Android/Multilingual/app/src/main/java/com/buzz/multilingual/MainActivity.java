package com.buzz.multilingual;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.Locale;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnGo = (Button) findViewById(R.id.btnGo);
        final RadioButton rbCN = (RadioButton) findViewById(R.id.rbCN);
        final RadioButton rbEn = (RadioButton) findViewById(R.id.rbEN);
        final RadioButton rbTW = (RadioButton) findViewById(R.id.rbTW);
        final RadioButton rbPT = (RadioButton) findViewById(R.id.rbPT);
        final RadioButton rbDef = (RadioButton) findViewById(R.id.rbDef);
        this.setTitle(R.string.app_name);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rbCN.isChecked()) {
                    setLanguage("cn");
                } else if (rbTW.isChecked()) {
                    setLanguage("tw");
                } else if (rbEn.isChecked()) {
                    setLanguage("en");
                } else if (rbPT.isChecked()) {
                    setLanguage("pt");
                } else if (rbDef.isChecked()) {
                    setLanguage("def");
                }

                //刷新界面
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }

    private void setLanguage(String lang) {
        Configuration config = getResources().getConfiguration();//获取系统的配置
        Locale curLocale = Locale.getDefault();
        switch (lang) {
            case "en":
                curLocale = Locale.ENGLISH;
                break;

            case "cn":
                curLocale = Locale.SIMPLIFIED_CHINESE;
                break;

            case "tw":
                curLocale = Locale.TAIWAN;
                break;

            case "pt":
            case "def":
                curLocale = new Locale(lang);
                break;
        }
        config.locale = curLocale;//将语言更改为当前语言
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());//更新配置
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
