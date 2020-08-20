package com.lambton.capstone_wic_fitandfine.util;

import android.text.TextUtils;


import com.lambton.capstone_wic_fitandfine.models.PainAreaData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static final String TAG = JsonUtil.class.getSimpleName();


    /**
     * Method to get the string from key cm json data.
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static JSONObject getJsonObjectFromJsonObject(JSONObject jsonObject,
                                                         String key) {
        JSONObject result = null;
        try {
            if (jsonObject != null) {
                result = jsonObject.optJSONObject(key);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception @ getJsonObjectFromJsonObject:" + e.toString());
        }
        return result;

    }


    /**
     * Method to get the string from key cm json data.
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static JSONArray getJsonArrayFromJsonObject(JSONObject jsonObject,
                                                       String key) {
        JSONArray result = null;
        try {
            if (jsonObject != null) {
                result = jsonObject.optJSONArray(key);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception @ getJsonArrayFromJsonObject:" + e.toString());
        }
        return result;

    }


    /**
     * Method to get the string from key cm json data.
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static String getStringFromJsonObject(JSONObject jsonObject,
                                                 String key) {
        String result = null;
        try {
            if (jsonObject != null) {
                result = jsonObject.optString(key, null);

                if (!TextUtils.isEmpty(result) && result.equalsIgnoreCase("null"))
                    result = null;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception @ getStringFromJsonObject:" + e.toString());
        }
        return result;

    }

    /**
     * Method to get the string from key cm json data.
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static long getLongFromJsonObject(JSONObject jsonObject,
                                             String key) {
        long result = 0;
        try {
            if (jsonObject != null) {
                result = jsonObject.optLong(key);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception @ getStringFromJsonObject:" + e.toString());
        }
        return result;

    }

    /**
     * Method to get the Float from key cm json data.
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static double getDoubleFromJsonObject(JSONObject jsonObject,
                                                 String key) {
        double result = 0;
        if (jsonObject != null) {
            result = jsonObject.optDouble(key);
        }
        return result;
    }


    /**
     * Method to get the int value from key present cm json data.
     *
     * @param jsonObject
     * @param value
     * @return
     */

    public static int getIntFromJsonObject(JSONObject jsonObject, String value) {
        int result = 0;
        try {
            result = jsonObject.optInt(value);
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception @ getIntFromJsonObject:" + e.toString());
        }
        return result;
    }

    /**
     * Method to get the boolean value from key present cm json data.
     *
     * @param jsonObject
     * @param value
     * @return
     */
    public static boolean getBooleanFromJsonObject(JSONObject jsonObject,
                                                   String value) {
        boolean result = false;
        try {
            result = jsonObject.getBoolean(value);
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception @ getBooleanFromJsonObject:" + e.toString());
            int numericResult = getIntFromJsonObject(jsonObject, value);
            result = numericResult == 1;
        }
        return result;
    }


    /**
     * Method to get List of JsonObject from JsonArray:
     */
    public static ArrayList<JSONObject> getListOfJsonObject(
            JSONArray jsonArray) {
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();

        if (jsonArray != null && jsonArray.length() > 0) {

            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject jsonObject = null;

                try {
                    jsonObject = jsonArray.getJSONObject(index);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jsonObject != null) {
                    list.add(jsonObject);
                }
            }
        }
        return list;
    }

    /**
     * Method to get List of Strings from JsonArray:
     */
    public static List<String> getListOfStringFromJsonArray(JSONArray jsonArray) {
        List<String> list = new ArrayList<String>();

        if (jsonArray != null && jsonArray.length() > 0) {

            for (int index = 0; index < jsonArray.length(); index++) {
                String jsonString = null;

                try {
                    jsonString = jsonArray.getString(index);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jsonString != null) {
                    list.add(jsonString);
                }
            }
        }
        return list;
    }

    public static boolean isSuccess(JSONObject jsonObject) {
        try {
            if (getBooleanFromJsonObject(jsonObject, "success") || getBooleanFromJsonObject(jsonObject, "sucess"))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isPaidUserNeeded(JSONObject jsonObject) {
        try {
            if (getIntFromJsonObject(jsonObject, "respCode") == 151)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isPaidUserNeeded(PainAreaData painAreaData) {
        try {
            if (painAreaData.getRespCode().equals("151"))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static int getResponseCode(JSONObject jsonObject) {
        try {
            return getIntFromJsonObject(jsonObject, "respCode");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }


    public static boolean isSessionTimedOut(JSONObject jsonObject) {
        try {
            if (getIntFromJsonObject(jsonObject, "respCode") == 408)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getMessage(JSONObject jsonObject) {
        return getStringFromJsonObject(jsonObject, "message");
    }
}
