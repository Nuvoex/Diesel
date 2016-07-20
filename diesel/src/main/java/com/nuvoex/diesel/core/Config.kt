package com.nuvoex.diesel.core

import android.content.Context
import android.util.Log
import com.nuvoex.diesel.Utils
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Shine on 19/07/16.
 */

class Config private constructor(var context: Context) {

    init {
        getLoginConfig();
    }


    companion object {
        var sInstance: Config? = null;
        fun getInstance(context: Context): Config? {
            if (sInstance == null) {
                sInstance = Config(context)
                Log.i("anshul", " sInstance" + sInstance);

            }
            return sInstance
        }
    }

    private var usernameLength: Int? = null;
    private lateinit var logoDrawable: String;
    private var baseUrl: String? = null;
    private lateinit var app_identity: String
    private lateinit var acceptHeader: String;

    private fun updateConfig(configJson: JSONObject) {
        usernameLength = configJson.optInt("user_name_length", 10)
        logoDrawable = configJson.optString("drawable");
        baseUrl = configJson.optString("base_url");
        app_identity = configJson.optString("identity");
        acceptHeader = configJson.optString("accept_header");
    }

    private lateinit var jsonString: String;

    private lateinit var jsonObject: JSONObject;

    fun getLoginConfig() {
        jsonString = Utils.getJsonFile(context, "diesel", context.getPackageName())
        try {
            jsonObject = JSONObject(jsonString)
            updateConfig(jsonObject)
        } catch (e: JSONException) {
            e.printStackTrace()

        }

    }

    fun getUserName(): Int? {
        return usernameLength;
    }

    fun getBannerLogo(): String {
        return logoDrawable;
    }

    fun getBaseUrl(): String? {
        return baseUrl;
    }

    fun getAppIdentity(): String? {
        return app_identity;
    }

    fun getAcceptHeader(): String? {
        return acceptHeader;
    }


}
