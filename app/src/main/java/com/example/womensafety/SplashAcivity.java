package com.example.womensafety;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashAcivity extends AppCompatActivity {

    private static final int SplashTimeOut=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acivity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashAcivity.this,MainActivity.class);
                startActivity(intent);

            }
        },SplashTimeOut);
    }

    @Override
    protected void onResume() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashAcivity.this,MainActivity.class);
                startActivity(intent);

            }
        },SplashTimeOut);
        super.onResume();
    }
}
