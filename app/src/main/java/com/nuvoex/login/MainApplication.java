package com.nuvoex.login;

import android.app.Application;

import com.nuvoex.diesel.Diesel;

/**
 * Created by Shine on 16/08/16.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Diesel.initialize(getApplicationContext());
    }
}
