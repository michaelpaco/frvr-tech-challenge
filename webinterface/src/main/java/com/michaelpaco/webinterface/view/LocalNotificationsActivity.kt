package com.michaelpaco.webinterface.view

import android.os.Bundle
import com.michaelpaco.webinterface.R
import com.michaelpaco.webinterface.util.Const.LOCAL_NOTIFICATIONS_PARAM

class LocalNotificationsActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_notifications)

        startWebView(LOCAL_NOTIFICATIONS_PARAM, R.id.webview_local_notifications)
    }
}
