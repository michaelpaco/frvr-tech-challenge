package com.michaelpaco.webinterface.model.interfaces

interface WebApp {
    fun addNotification(
        displayTime: String,
        id: Int,
        title: String,
        message: String
    )
    fun closeWebView()
    fun deleteNotification(notification: String)
    fun getLifecycleEvents(): String
    fun getNotifications(): String
    fun showMessageFromWebView(message: String)
    fun showToast(message: String, type: String)
}
