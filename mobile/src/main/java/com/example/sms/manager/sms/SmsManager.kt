package com.example.sms.manager.sms

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import com.example.sms.manager.permission.PermissionCallback
import com.example.sms.manager.permission.PermissionManager
import com.example.sms.manager.util.Logger

object SmsManager {
    private var smsReceiver: SmsReceiver? = null
    private val permissions: List<String> =
        listOf<String>(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
    var smsCallback: ((String) -> Unit)? = null

    //todo 初始化传入context并注册
    fun init(activity: Activity) {
        val needRequireList = PermissionManager.checkPermissions(activity, permissions)
        Logger.i("need require permission: $permissions")
        if (needRequireList.size != 0) {
            Logger.i("unable permissions: $needRequireList")
            PermissionManager.requestPermission(
                activity,
                needRequireList,
                object : PermissionCallback {
                    override fun onSuccess() {
                        Logger.i("permission require success.")
                        register(activity)
                    }

                    override fun onFail(list: Array<String>) {
                        Logger.w("permission require fail. list: $list")
                    }

                })
        } else {
            Logger.i("permission is ok.")
            register(activity)
        }
    }

    private fun register(context: Context) {
        Logger.i("register receiver.")
        // 使用广播进行监听
        val smsFilter = IntentFilter()
        smsFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        smsFilter.addAction("android.provider.Telephony.SMS_DELIVER");
        if (smsReceiver == null) {
            smsReceiver = SmsReceiver()
        }
        context.registerReceiver(smsReceiver, smsFilter)
        Logger.i("register success.")
    }


    //todo 自动获取短信验证码


}