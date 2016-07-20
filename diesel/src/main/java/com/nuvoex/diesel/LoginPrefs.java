package com.nuvoex.diesel;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.nuvoex.diesel.model.UserInfo;

import java.lang.reflect.Type;

public class LoginPrefs {

    private final static String PREFS_NAME = "LoginPrefs";
    private SharedPreferences mSharedPreferences;
    private static LoginPrefs sInstance;

    private final String IS_USER_LOGGED_IN = "isUserLoggedIn";
    private final String TOKEN = "token";
    private final String USER_INFO = "userinfo";

    private LoginPrefs(Context c) {
        mSharedPreferences = c.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
    }

    public static LoginPrefs getInstance() {
        if (sInstance == null) {
            sInstance = new LoginPrefs(LoginApp.sInstance.getApplicationContext());
        }
        return sInstance;
    }

    public void clearSharedPrefs() {
        mSharedPreferences.edit().clear().apply();
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    private void setBoolean(String key, boolean value) {
        //Reason for changing commit() to apply()
        //http://stackoverflow.com/questions/5960678/whats-the-difference-between-commit-and-apply-in-shared-preference
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    private String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    private void setString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    private void setObject(String key, Object object) {
        String jsonString = new Gson().toJson(object);
        setString(key, jsonString);
    }

    private Object getObject(String key, Type typeOfT) {
        String jsonString = getString(key, "");
        return new Gson().fromJson(jsonString, typeOfT);
    }

    public Boolean getIsUserLoggedIn() {
        return getBoolean(IS_USER_LOGGED_IN, false);
    }

    public void setIsUserLoggedIn(Boolean isUserLoggedIn) {
        setBoolean(IS_USER_LOGGED_IN, isUserLoggedIn);
    }

    public String getToken() {
        return getString(TOKEN, "");
    }

    public void setToken(String token) {
        setString(TOKEN, token);
    }

    public void setUserInfo(UserInfo userInfo) {
        setObject(USER_INFO, userInfo);
    }

    public UserInfo getUserInfo() {
        return (UserInfo) getObject(USER_INFO, UserInfo.class);
    }

}