package com.example.sms.manager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.example.sms.manager.notification.NotificationManager
import com.example.sms.manager.permission.PermissionManager
import com.example.sms.manager.sms.SmsManager
import com.example.sms.manager.util.Logger

@SuppressLint("StaticFieldLeak")
object Mobile {
    private lateinit var activity: Activity
    fun init(_activity: Activity){
        Logger.i("Mobile init.")
        activity = _activity
        SmsManager.init(activity)
        PermissionManager.init(activity)
        NotificationManager.createChannel(channelId = "game", channelName = "Game Notify")
    }

    fun getContext(): Context{
        return activity
    }

    fun setSMSCallback(callback: (String)-> Unit){
        SmsManager.smsCallback = callback
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}