package com.lambton.capstone_wic_fitandfine.util;

import android.util.Log;


import com.lambton.capstone_wic_fitandfine.BuildConfig;


/**
 * Created by IBM_ADMIN on 3/3/2017.
 */

public class LogUtil {

    private static final boolean IS_DEBUG_MODE = BuildConfig.DEBUG;

    public static void d(String tag, String message) {
        if (IS_DEBUG_MODE) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (IS_DEBUG_MODE) {
            Log.i(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (IS_DEBUG_MODE) {
            Log.e(tag, message);
        }
    }
}
