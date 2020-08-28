package com.lambton.capstone_wic_fitandfine.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lambton.capstone_wic_fitandfine.R;

public class SplashActivity extends AppCompatActivity {

    private final int duration = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, duration);
    }
}
