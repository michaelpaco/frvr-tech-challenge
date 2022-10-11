package com.michaelpaco.webinterface

import android.content.Context
import android.content.Intent
import com.michaelpaco.webinterface.view.LifecycleEventsActivity
import com.michaelpaco.webinterface.view.LocalNotificationsActivity
import com.michaelpaco.webinterface.view.NativeLogsActivity

open class WebInterfaceLib(private val context: Context) {
    fun openNativeLogs() {
        context.startActivity(Intent(context, NativeLogsActivity::class.java))
    }

    fun openLocalNotifications() {
        context.startActivity(Intent(context, LocalNotificationsActivity::class.java))
    }

    fun openLifecycleEvents() {
        context.startActivity(Intent(context, LifecycleEventsActivity::class.java))
    }
}
