package com.lambton.capstone_wic_fitandfine.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.lambton.capstone_wic_fitandfine.R;
import com.lambton.capstone_wic_fitandfine.common.IAppConstants;
import com.lambton.capstone_wic_fitandfine.util.SharedPrefUtil;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textview_profile_allergies_number;
    private TextView textview_profile_preexisting_conditions_number;
    private TextView textview_profile_medications_number;
    private TextView textview_profile_supplements_number;
    private TextView textview_profile_my_physician;
    private TextView textview_profile_name;
    private TextView textview_profile_level;
    private TextView textview_profile_points;
    private TextView textview_profile_member;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    // private PhysicianResponse physicianResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_profile);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(mToolbar);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initUI();
        String userID = SharedPrefUtil.getString(ProfileActivity.this, IAppConstants.KEY_USER_ID);

        Log.d("ProfileActivity","USERID: "+ userID);
    }

    private void setSupportActionBar(Toolbar mToolbar) {
    }


    private void initUI() {

        textview_profile_member = (TextView) findViewById(R.id.textview_profile_member);
        textview_profile_name = (TextView) findViewById(R.id.textview_profile_name);
        textview_profile_level = (TextView) findViewById(R.id.textview_profile_level);
        textview_profile_points = (TextView) findViewById(R.id.textview_profile_points);
        TextView textview_profile_refer_a_friend = (TextView) findViewById(R.id.textview_profile_refer_a_friend);
        TextView textview_profile_learn_more = (TextView) findViewById(R.id.textview_profile_learn_more);

        findViewById(R.id.textview_profile_close).setOnClickListener(this);
        findViewById(R.id.layout_profile_personal_info).setOnClickListener(this);
        textview_profile_refer_a_friend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_profile_close:
                finish();
                break;

            case R.id.layout_profile_personal_info:

                break;
            case R.id.textview_profile_refer_a_friend:
                signOut();
                break;
        }
    }


    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        //  Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        Toast.makeText(ProfileActivity.this, R.string.toast_logged_out, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });
    }

    }
