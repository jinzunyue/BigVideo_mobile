package com.pbtd.mobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pbtd.mobile.Constants;

/**
 * Created by xuqinchao on 17/4/24.
 */

public class SharedPreferencesUtil {
    public static void saveComboCode(Context context, String comboCode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("comboCode", comboCode).commit();
    }

    public static String getComboCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("comboCode", "");
    }

    public static void saveMacAddress(Context context, String macAddress){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("macAddress", macAddress).commit();
    }

    public static String getMacAddress(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("macAddress", "");
    }

    public static void saveServiceComboCode(Context context, String serviceComboCode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("serviceComboCode", serviceComboCode).commit();
    }

    public static String getServiceComboCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("serviceComboCode", "");
    }
}
