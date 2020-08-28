package com.lambton.capstone_wic_fitandfine.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lambton.capstone_wic_fitandfine.R;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome);
            initUIViews();
        }

        private void initUIViews(){
            findViewById(R.id.activity_welcome_get_started).setOnClickListener(this);
            findViewById(R.id.activity_welcome_log_in).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.activity_welcome_get_started:
                    Log.d("WelcomeActivity", "CLICKED");
                    startActivity(new Intent(this,
                            LoginActivity.class));
                    break;
                case R.id.activity_welcome_log_in:
                 //   NavUtils.navigateUpFromSameTask(this);
                    startActivity(new Intent(this,
                            LoginActivity.class));
            }
        }

        private void startedButtonClicked() {
            startActivity(new Intent(this,
                    RegistrationFormActivity.class));
        }
    }
