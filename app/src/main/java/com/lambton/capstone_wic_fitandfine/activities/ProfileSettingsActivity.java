package com.lambton.capstone_wic_fitandfine.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.models.SettingsData;

public class ProfileSettingsActivity extends AppCompatActivity {
    private TextView TurnPushNotification;
    private SwitchCompat switch_email;
    private SwitchCompat switch_text;
    private SwitchCompat switch_touchid;
    private SettingsData settingsData;
    private TextView text_standard;
    private TextView text_metric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
    }

        private void initUI() {
            TurnPushNotification = (TextView) findViewById(R.id.TurnPushNotification);
            text_standard = (TextView) findViewById(R.id.text_standard);
            text_metric = (TextView) findViewById(R.id.text_metric);
            switch_email = (SwitchCompat) findViewById(R.id.switch_email);
            switch_text = (SwitchCompat) findViewById(R.id.switch_text);
            switch_touchid = (SwitchCompat) findViewById(R.id.switch_touchid);



        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
           // getMenuInflater().inflate(R.menu.menu_done, menu);
            return true;
        }


   /* private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(getResources().getString(R.string.action_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }*/


}
