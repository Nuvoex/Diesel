package com.nuvoex.diesel

import android.app.Application
import android.content.Context

/**
 * Created by Shine on 20/07/16.
 */
class LoginApp : Application() {


    override fun onCreate() {
        super.onCreate()
        sInstance = this;
    }

    companion object {
        lateinit var sInstance: LoginApp;
    }

}