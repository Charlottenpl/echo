package com.example.sms.manager.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.example.sms.manager.util.Logger

class SmsReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        // 确定是sms_receiver
        if (p1 == null || !p1.action.equals("android.provider.Telephony.SMS_RECEIVED"))
            return
        Logger.i("get sms receiver.")

        val bundle: Bundle? = p1.extras
        if (bundle?.get("pdus") == null)
            return

        val objs: Array<*>? = bundle.get("pdus") as? Array<*>
        for (pdu in objs!!) {
            val smsMessage: SmsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
            // 短信号码
            val sender: String = smsMessage.displayOriginatingAddress
            //短信内容
            val content: String = smsMessage.displayMessageBody
            Logger.d("onReceive: from = $sender\n $content")

            //todo 需要提取验证码
            val code = ""
            SmsManager.smsCallback?.let { it(code) }
        }

    }
}