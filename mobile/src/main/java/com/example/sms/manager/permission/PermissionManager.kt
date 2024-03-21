package com.example.sms.manager.permission

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager() {
    companion object {
        const val REQUEST_CODE_PERMISSION = 123
        var requestCallback: PermissionCallback? = null

        fun init(activity: Activity){

        }


        fun checkPermissions(activity: Activity, permissions: List<String>): MutableList<String> {
            var requires = mutableListOf<String>()
            for (permission: String in permissions){
                val permissionState = ContextCompat.checkSelfPermission(activity, permission)
                if (permissionState != PackageManager.PERMISSION_GRANTED)
                    requires.add(permission)
            }
            return requires
        }

        fun requestPermission(activity: Activity, permissions: List<String>, callback: PermissionCallback) {
//            activity.requestPermissions(permissions.toTypedArray(), REQUEST_CODE_PERMISSION)

            requestCallback = callback
          ActivityCompat.requestPermissions(activity,permissions.toTypedArray(), REQUEST_CODE_PERMISSION)
        }


        /**
         * onRequestPermissionsResult
         *
         */
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean {
            if (requestCode == REQUEST_CODE_PERMISSION) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    requestCallback?.onSuccess()
                    return true
                }
            }
            // Permission denied
            requestCallback?.onFail(arrayOf())
            return false
        }
    }
}

interface PermissionCallback{
    fun onSuccess()

    fun onFail(list: Array<String>)
}