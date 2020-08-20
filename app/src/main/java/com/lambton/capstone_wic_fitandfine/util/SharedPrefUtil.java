package com.lambton.capstone_wic_fitandfine.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SharedPrefUtil {

    public final static String KEY_IS_FIRST_SURVEY= "IsFirstSurvey";
    public final static String SP_KEY_FINGERPRINT_PERM_ASKED = "SP_KEY_FINGERPRINT_PERM_ASKED";
    public  static String KEY_COOKIE="";
    //JSESSIONID=0000yBie6cLVuPUs797mHjApXPP:9bc777e7-fa38-401f-b364-cea06ad1ffc8;
    // Shared Pref.
    static String PREF_NAME = "USER_PREFS";

    /*****************************************************************
     * Method to save objects to Shared prefs
     *****************************************************/
    public static void saveBoolean(Context context, String key, boolean value) {

        SharedPreferences settings = context.getSharedPreferences(
                PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean(key, value);

        // Commit the edits!
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {

        SharedPreferences shrdPref = context.getSharedPreferences(
                PREF_NAME, 0);
        return shrdPref.getBoolean(key, false);
    }

    // **Method for saving Key value pairs cm Shared Prefrences**/

    public static void saveString(Context context, String key, String value) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveFloat(Context context, String key, float value) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putFloat(key, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveDate(Context context, String key, Date value) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong(key, value != null ? value.getTime() : 0);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveDouble(Context context, String key, double value) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putFloat(key, (float) value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void saveInt(Context context, String key, int value) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(key, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // *****************Method to get values from Shared prefs. *******/
    public static String getString(Context context, String key) {
        String value = null;

        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
            value = sharedPref.getString(key, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static Date getDate(Context context, String key) {
        Date value = null;

        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
            long dateValue = sharedPref.getLong(key, 0);
            value = new Date(dateValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


    public static float getFloat(Context context, String key) {
        float value = 0;

        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
            value = sharedPref.getFloat(key, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


    public static double getDouble(Context context, String key) {
        double value = 0;

        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
            value = sharedPref.getFloat(key, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static int getInt(Context context, String key) {
        int value = 0;
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(
                    PREF_NAME, Context.MODE_PRIVATE);
            value = sharedPref.getInt(key, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


    /**
     * Method to delete specific key from shared pref.
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean removeKeyFromSharedPref(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(
                PREF_NAME, Context.MODE_PRIVATE);
        boolean isRemoved = settings.edit().remove(key).commit();
        return isRemoved;
    }


}
