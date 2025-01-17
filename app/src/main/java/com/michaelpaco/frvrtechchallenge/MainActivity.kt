package com.michaelpaco.frvrtechchallenge

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.michaelpaco.webinterface.WebInterfaceLib

class MainActivity : AppCompatActivity() {
    private lateinit var webInterfaceLib: WebInterfaceLib

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webInterfaceLib = WebInterfaceLib(this)

        setupButtons()
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.btn_native_logs).setOnClickListener {
            webInterfaceLib.openNativeLogs()
        }

        findViewById<Button>(R.id.btn_local_notifications).setOnClickListener {
            webInterfaceLib.openLocalNotifications()
        }

        findViewById<Button>(R.id.btn_lifecycle_events).setOnClickListener {
            webInterfaceLib.openLifecycleEvents()
        }
    }
}
