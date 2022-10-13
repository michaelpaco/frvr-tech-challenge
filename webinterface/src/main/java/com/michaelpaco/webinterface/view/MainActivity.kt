package com.michaelpaco.webinterface.view

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.michaelpaco.webinterface.util.Const.JAVASCRIPT_INTERFACE_NAME
import com.michaelpaco.webinterface.util.Const.LOCAL_BASE_URL
import com.michaelpaco.webinterface.util.WebAppImpl

abstract class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    fun startWebView(urlParam: String, id: Int) {
        webView = findViewById<WebView?>(id).apply {
            loadUrl("$LOCAL_BASE_URL?app=$urlParam")
            webChromeClient = WebChromeClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            addJavascriptInterface(WebAppImpl(this@MainActivity), JAVASCRIPT_INTERFACE_NAME)
        }

        WebView.setWebContentsDebuggingEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        webView.reload()
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
