package com.michaelpaco.webinterface.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.michaelpaco.webinterface.model.data.NotificationEntity
import com.michaelpaco.webinterface.model.database.LifecycleEventDatabase
import com.michaelpaco.webinterface.model.database.LocalNotificationDatabase
import com.michaelpaco.webinterface.model.interfaces.WebApp
import com.michaelpaco.webinterface.repository.LifecycleEventRepository
import com.michaelpaco.webinterface.repository.LocalNotificationRepository
import com.michaelpaco.webinterface.util.Const.WEBVIEW_MESSAGE_TAG

class WebAppImpl(private val context: Context) : WebApp {
    private val gson = Gson()
    private val localNotifications = LocalNotifications()

    private val localNotificationRepository =
        LocalNotificationRepository.getInstance(
            LocalNotificationDatabase.getDatabase(context).notificationDao()
        )

    private val lifecycleEventsRepository =
        LifecycleEventRepository.getInstance(
            LifecycleEventDatabase.getDatabase(context).lifecycleEventDao()
        )

    @JavascriptInterface
    override fun showMessageFromWebView(message: String) {
        if (message.isEmpty()) return

        Log.d(WEBVIEW_MESSAGE_TAG, message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @JavascriptInterface
    override fun addNotification(
        displayTime: String,
        id: Int,
        title: String,
        message: String
    ) {
        NotificationEntity(
            id,
            displayTime,
            title,
            message
        ).apply {
            localNotifications.setNotification(this, context, false)
            localNotificationRepository.add(this)
        }
    }

    @JavascriptInterface
    override fun getNotifications(): String {
        return gson.toJson(localNotificationRepository.getAll())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @JavascriptInterface
    override fun deleteNotification(notification: String) {
        gson.fromJson(notification, NotificationEntity::class.java).apply {
            localNotifications.setNotification(this, context, true)
            localNotificationRepository.delete(this)
        }
    }

    @JavascriptInterface
    override fun closeWebView() {
        (context as Activity).finish()
    }

    @JavascriptInterface
    override fun showToast(message: String, type: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    override fun getLifecycleEvents(): String {
        return gson.toJson(lifecycleEventsRepository.getAll())
    }
}
