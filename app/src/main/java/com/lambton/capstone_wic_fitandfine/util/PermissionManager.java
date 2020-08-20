package com.lambton.capstone_wic_fitandfine.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;


public class PermissionManager {

    private static final int REQUEST_CODE_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_FINGERPRINT_AUTHENTICATION = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static String[] PERMISSIONS_FINGERPRINT_AUTHENTICATION = {
            Manifest.permission.USE_FINGERPRINT
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_CODE_EXTERNAL_STORAGE
            );
        }
    }

    public static void verifyFingerPrintPermissions(Activity activity) {
        boolean isAlreadyAsked = SharedPrefUtil.getBoolean(activity, SharedPrefUtil.SP_KEY_FINGERPRINT_PERM_ASKED);

        if (!isAlreadyAsked) {

            SharedPrefUtil.saveBoolean(activity, SharedPrefUtil.SP_KEY_FINGERPRINT_PERM_ASKED, true);

            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.USE_FINGERPRINT);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_FINGERPRINT_AUTHENTICATION,
                        REQUEST_CODE_FINGERPRINT_AUTHENTICATION
                );
            }
        }
    }
}
