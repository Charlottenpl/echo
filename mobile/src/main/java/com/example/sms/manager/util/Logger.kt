package com.example.sms.manager.util

import android.util.Log
import com.example.sms.manager.util.Kolor.ESCAPE

class Logger {
    companion object{

        const val TAG: String = "Echo"

        fun e(content: String){
            Log.e(TAG, getLog(content))
        }

        fun w(content: String){
            Log.w(TAG, getLog(content))
        }

        fun i(content: String){
            Log.i(TAG, getLog(content))
        }

        fun d(content: String){
            Log.d(TAG, getLog(content))
        }


        fun getLog(msg: String): String {
            val (className, methodName) = getCallerInfo()
            return "$className.$methodName() 🌈| $msg"
        }

        /**
         * 获取类名和方法信息
         */
        fun getCallerInfo(): Pair<String, String> {
            val stackTrace = Throwable().stackTrace
            val callerIndex = 4 // 通常是调用者的索引，但可能需要根据实际情况调整
            val stackTraceElement = stackTrace.getOrNull(callerIndex) ?: return Pair("Unknown", "Unknown")
            return Pair(stackTraceElement.className.substringAfterLast('.').substringBefore("$"), stackTraceElement.methodName.substringBefore("$"))
        }
    }
}


enum class Level{
    Debug, Info, Warning, Error
}
