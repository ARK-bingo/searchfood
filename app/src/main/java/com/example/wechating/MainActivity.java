package com.example.wechating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signin;
    private Button newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.initialize(this);

        Log.d("haha", "onCreate: haha");

        signin = (Button) findViewById(R.id.signin);
        newUser = (Button) findViewById(R.id.newUser);
        signin.setOnClickListener(this);
        newUser.setOnClickListener(this);

        BroadcastReceiver br = new myreceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(br, filter);
        Intent intents = new Intent();
        intents.setAction("com.example.broadcattest.MY-broadCast");
        sendBroadcast(intents);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.signin) {//转到登录界面
            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.newUser) {//转到注册界面
            intent = new Intent(MainActivity.this, RegistActivity.class);
            startActivity(intent);
        }
    }
}