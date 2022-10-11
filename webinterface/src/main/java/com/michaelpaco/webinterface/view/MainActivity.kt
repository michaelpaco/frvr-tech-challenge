package com.michaelpaco.webinterface.view

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.michaelpaco.webinterface.util.WebAppImpl

abstract class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    fun startWebView(urlParam: String, id: Int) {
        webView = findViewById<WebView?>(id).apply {
            loadUrl("file:///android_asset/webview/index.html?app=$urlParam")
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            addJavascriptInterface(WebAppImpl(this@MainActivity), "Android")
        }

        WebView.setWebContentsDebuggingEnabled(true)
    }

    private fun destroyWebView() {
        webView.clearHistory()
        webView.destroy()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyWebView()
    }
}
