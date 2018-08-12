package com.yellowspace.coffee.yellowspace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    private static String TAG =  "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        System.out.println("in splashActivity");

        Log.e(TAG, "in splash activity");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
