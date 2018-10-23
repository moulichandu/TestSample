package com.example.user.testsample

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat.getSystemService
import java.util.*

class MyApp:Application() {
    companion object {
        lateinit var myApp: MyApp
    }
    override fun onCreate() {
        super.onCreate()
        myApp=this;
    }
    public fun  checkNetwork():Boolean
    {

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                return true
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                return true
        }
        return false;
    }
}