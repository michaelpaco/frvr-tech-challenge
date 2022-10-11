package com.michaelpaco.webinterface.view

import android.os.Bundle
import com.michaelpaco.webinterface.R
import com.michaelpaco.webinterface.util.Const.LIFECYCLE_EVENTS_PARAM

class LifecycleEventsActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_events)

        startWebView(LIFECYCLE_EVENTS_PARAM, R.id.webview_lifecycle_events)
    }
}
