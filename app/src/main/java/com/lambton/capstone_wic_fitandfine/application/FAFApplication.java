package com.lambton.capstone_wic_fitandfine.application;

import android.app.Application;
import android.content.Context;

public class FAFApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}