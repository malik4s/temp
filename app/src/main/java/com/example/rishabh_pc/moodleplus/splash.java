package com.example.rishabh_pc.moodleplus;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

///**
// * Created by Rishabh-pc on 11-Jan-16.
// */
public class splash extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(splash.this, Login.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}
