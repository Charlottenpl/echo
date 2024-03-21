package com.example.sms.manager

import android.app.Activity
import com.example.sms.manager.permission.PermissionManager
import com.example.sms.manager.sms.SmsManager
import com.example.sms.manager.util.Logger

class Mobile {
    companion object{
        fun init(activity: Activity){
            Logger.i("Mobile init.")
            SmsManager.init(activity)
            PermissionManager.init(activity)
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
}