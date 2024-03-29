package com.sky.echo

import android.app.Application
import android.util.Log
import com.netease.nis.quicklogin.listener.QuickLoginPreMobileListener
import com.sky.echo.util.DataUtil
import com.sky.quick_login.QuickLoginManager

class MyApp: Application(){

    override fun onCreate() {
        super.onCreate()
        Log.e("MyApp", "onCreate: init", )
        DataUtil.init(this)
        QuickLoginManager.init("bdbf047a00cb405f8cc15d6ffedac716", applicationContext)
        QuickLoginManager.prefetchNum(object : QuickLoginPreMobileListener() {
            override fun onGetMobileNumberSuccess(p0: String?, p1: String?) {
                Log.e("QuickLogin", "onGetMobileNumberSuccess: $p0 : $p1", )
            }

            override fun onGetMobileNumberError(p0: String?, p1: String?) {
                Log.e("QuickLogin", "onGetMobileNumberError: $p0 : $p1", )
            }
        })
    }
}