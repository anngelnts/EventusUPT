package com.desarrollo.eventusupt.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveSharedPreference {

    private static final String AUTH_PREFERENCES = "auth_preferences";
    private static final String LOGGED_TOKEN = "logged_token";
    private static final String LOGGED_STATUS = "logged_status";
    private static final String LOGGED_TYPE = "0";

    private static SharedPreferences getOnPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void setLoggedIn(Context context, String token, boolean loggedIn, String loggedType){
        SharedPreferences.Editor editor = getOnPreferences(context).edit();
        editor.putString(LOGGED_TOKEN, token);
        editor.putBoolean(LOGGED_STATUS, loggedIn);
        editor.putString(LOGGED_TYPE, loggedType);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context){
        return getOnPreferences(context).getBoolean(LOGGED_STATUS, false);
    }

    public static String getLoggedToken(Context context){
        return getOnPreferences(context).getString(LOGGED_TOKEN, "");
    }

    public static String getLoggedType(Context context){
        return getOnPreferences(context).getString(LOGGED_TYPE, "");
    }
}
