package com.michaelpaco.webinterface.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.michaelpaco.webinterface.model.data.NotificationEntity

class NotificationScheduler : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val userNotification = NotificationEntity(
            intent.getIntExtra("id", 0),
            intent.getStringExtra("displayTime")!!,
            intent.getStringExtra("title")!!,
            intent.getStringExtra("message")!!
        )

        LocalNotifications().registerNotification(userNotification)
    }
}
