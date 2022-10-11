package com.michaelpaco.webinterface.model.interfaces

interface WebApp {
    fun showMessageFromWebView(message: String)
    fun registerNotification(
        displayTime: String,
        id: Int,
        title: String,
        message: String
    )
    fun getAllNotifications(): String
}
