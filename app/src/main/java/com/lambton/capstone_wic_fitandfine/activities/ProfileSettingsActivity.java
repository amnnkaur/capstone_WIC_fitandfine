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

public class ProfileSettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    private TextView TurnPushNotification;
    private SwitchCompat switch_email;
    private SwitchCompat switch_text;
    private SwitchCompat switch_touchid;
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
            switch_email.setOnCheckedChangeListener(this);
            switch_text.setOnCheckedChangeListener(this);
            switch_touchid.setOnCheckedChangeListener(this);
            text_standard.setOnClickListener(this);
            text_metric.setOnClickListener(this);
            TurnPushNotification.setOnClickListener(this);

        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_done, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.menuDone:
                    mPresenter.setProfileSettingValues(settingsData);

              /*  Intent dataIntent = new Intent();
                dataIntent.putExtra(Intent.EXTRA_TEXT, mPresenter.getAllergyData());
                setResult(RESULT_OK, dataIntent);
                finish();*/
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }


   /* private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(getResources().getString(R.string.action_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }*/

        @Override
        public void updateProfileSetting(SettingsData settingsData){
            this.settingsData = settingsData;
            if (settingsData.getPushNotificationEnabled() == 1)
                TurnPushNotification.setTextColor(ContextCompat.getColor(this, R.color.blue));
            else
                TurnPushNotification.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));

            if (settingsData.getEmailNotificationEnabled() == 1)
                switch_email.setChecked(true);
            else
                switch_email.setChecked(false);


            if (settingsData.getSmsNotificationEnabled() == 1)
                switch_text.setChecked(true);
            else
                switch_text.setChecked(false);

            if (settingsData.getMeasurementType().equalsIgnoreCase("standard")) {
                text_standard.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
                text_standard.setTextColor(ContextCompat.getColor(this, R.color.white));

                text_metric.setBackgroundColor(ContextCompat.getColor(this, R.color.settings_back));
                text_metric.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));
            } else {
                text_metric.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
                text_metric.setTextColor(ContextCompat.getColor(this, R.color.white));

                text_standard.setBackgroundColor(ContextCompat.getColor(this, R.color.settings_back));
                text_standard.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));
            }


        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.text_standard:
                this.settingsData.setMeasurementType("standard");
                text_standard.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
                text_standard.setTextColor(ContextCompat.getColor(this, R.color.white));

                text_metric.setBackgroundColor(ContextCompat.getColor(this, R.color.settings_back));
                text_metric.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));

                break;
            case R.id.text_metric:
                this.settingsData.setMeasurementType("metric");

                text_metric.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
                text_metric.setTextColor(ContextCompat.getColor(this, R.color.white));

                text_standard.setBackgroundColor(ContextCompat.getColor(this, R.color.settings_back));
                text_standard.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));

                break;
            case R.id.TurnPushNotification:
                if(this.settingsData.getPushNotificationEnabled()==1){
                    this.settingsData.setPushNotificationEnabled(0);
                    TurnPushNotification.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));
                }
                else {
                    this.settingsData.setPushNotificationEnabled(1);
                    TurnPushNotification.setTextColor(ContextCompat.getColor(this, R.color.blue));
                }

                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()){
            case R.id.switch_email:
                if(b)
                    this.settingsData.setEmailNotificationEnabled(1);
                else
                    this.settingsData.setEmailNotificationEnabled(0);
                break;
            case R.id.switch_touchid:
               /* if(b)
                    this.settingsData.setEmailNotificationEnabled(1);
                else
                    this.settingsData.setEmailNotificationEnabled(0);*/
                break;
            case R.id.switch_text:
                if(b)
                    this.settingsData.setSmsNotificationEnabled(1);
                else
                    this.settingsData.setSmsNotificationEnabled(0);
                break;
        }


    }
}
