package com.nuvoex.diesel

import com.nuvoex.diesel.model.UserInfo

/**
 * Created by Shine on 20/07/16.
 */
public object LoginUtils {

    public fun isLoggedIn(): Boolean {
        return LoginPrefs.getInstance().isUserLoggedIn;
    }

    fun logout() {
        LoginPrefs.getInstance().clearSharedPrefs();
    }

    public fun getUserInfo(): UserInfo {
        return LoginPrefs.getInstance().userInfo
    }
}