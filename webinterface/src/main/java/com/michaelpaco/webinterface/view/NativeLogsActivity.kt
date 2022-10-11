package com.michaelpaco.webinterface.view

import android.os.Bundle
import com.michaelpaco.webinterface.R
import com.michaelpaco.webinterface.util.Const.NATIVE_LOGS_PARAM

class NativeLogsActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_logs)

        startWebView(NATIVE_LOGS_PARAM, R.id.webview_native_logs)
    }
}
