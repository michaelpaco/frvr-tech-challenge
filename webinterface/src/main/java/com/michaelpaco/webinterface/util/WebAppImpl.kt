package com.michaelpaco.webinterface.util

import android.content.Context
import android.os.Build
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.michaelpaco.webinterface.model.data.NotificationEntity
import com.michaelpaco.webinterface.model.database.LocalNotificationDatabase
import com.michaelpaco.webinterface.model.interfaces.WebApp
import com.michaelpaco.webinterface.repository.LocalNotificationRepository

class WebAppImpl(private val context: Context) : WebApp {
    private val gson = Gson()
    private val localNotifications = LocalNotifications()
    private val localNotificationRepository =
        LocalNotificationRepository.getInstance(
            LocalNotificationDatabase.getDatabase(context).notificationDao()
        )

    @JavascriptInterface
    override fun showMessageFromWebView(message: String) {
        if (message.isEmpty()) return

        Log.d("WebViewMessage", message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @JavascriptInterface
    override fun registerNotification(
        displayTime: String,
        id: Int,
        title: String,
        message: String
    ) {
        val notification = NotificationEntity(
            id,
            displayTime,
            title,
            message
        )

        localNotifications.schedule(notification, context)
        localNotificationRepository.addNotification(notification)

        Log.d("Notification", notification.toString())
    }

    @JavascriptInterface
    override fun getAllNotifications(): String {
        val notifications = localNotificationRepository.getAllNotifications()

        return gson.toJson(notifications)
    }
}
