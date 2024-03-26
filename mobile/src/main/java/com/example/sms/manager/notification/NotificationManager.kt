package com.example.sms.manager.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.sms.manager.Mobile
import com.example.sms.manager.util.Logger


/**
 * 通知操作类
 */
class NotificationManager {

    /**
     * todo
     * 1. 自定义icon
     * 2. 自定义文本内容、
     * 3. 自定义点击事件
     */
    companion object {


        /**
         * 创建通知渠道
         */
        fun createChannel(channelId: String = "", channelName: String = "") {
            val context = Mobile.getContext()

            // 从Android 8.0 后引入通知渠道，允许用户自定义想看到的通知类型和行为
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                val dailyChannel: NotificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    setShowBadge(false)
                    enableLights(true)
                    enableVibration(true)
                    lightColor = Color.RED
                }

                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(dailyChannel)
            }
        }


        /**
         * 发送通知的函数。
         *
         * @param title 通知的标题。
         * @param message 通知显示的消息内容。
         * @param smallIcon 通知小图标资源ID。
         * @param intentClass 点击通知后启动的Activity类。
         */
        fun send(title: String, message: String, smallIcon: Int, intentClass: Class<*>) {
            var isavaliable = areNotificationsEnabled()
            Logger.e("notify ==== $isavaliable")
            // 获取应用程序的上下文环境
            val context = Mobile.getContext()

            // 创建Intent，并设置启动标志
            val intent = Intent(context, intentClass)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            // 创建一个PendingIntent，用于在通知被点击时启动相应的Activity
            val pendingIntent = PendingIntent.getActivity(
                context,
                0, // 使用一个唯一的请求代码
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // 构建通知，设置标题、内容、小图标等
            val notification = NotificationCompat.Builder(context, "game")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(smallIcon)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, smallIcon))
                .setAutoCancel(true)                   // 设置通知被点击后自动取消
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            // 获取通知管理器，并发送通知
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.notify(0, notification)
        }


        /**
         * Return whether the notifications enabled.
         *
         * @return `true`: yes<br></br>`false`: no
         */
        fun areNotificationsEnabled(): Boolean {
            return NotificationManagerCompat.from(Mobile.getContext()).areNotificationsEnabled()
        }

    }


}

