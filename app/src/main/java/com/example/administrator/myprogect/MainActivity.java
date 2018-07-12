package com.example.administrator.myprogect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cloudlive.activity.LoginActivity;
import cloudlive.util.ActivityUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoLive(View view) {
        ActivityUtil.jump(getApplicationContext(), LoginActivity.class);
    }
}
