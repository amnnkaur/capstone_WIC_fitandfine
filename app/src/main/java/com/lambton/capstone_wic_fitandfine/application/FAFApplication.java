package com.lambton.capstone_wic_fitandfine.application;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

public class FAFApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}