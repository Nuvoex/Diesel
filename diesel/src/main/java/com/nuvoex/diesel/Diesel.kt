package com.nuvoex.diesel

import android.content.Context
import com.nuvoex.diesel.core.Config

/**
 * Created by Shine on 16/08/16.
 */
object Diesel {

   @JvmStatic public fun initialize(context: Context) {
        Config.getInstance(context)
        mApplicationContext = context
    }

    private lateinit var mApplicationContext: Context;

    fun getApplicationContext(): Context = mApplicationContext

}