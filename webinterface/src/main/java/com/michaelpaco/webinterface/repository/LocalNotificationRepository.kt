package com.michaelpaco.webinterface.repository

import com.michaelpaco.webinterface.model.data.NotificationEntity
import com.michaelpaco.webinterface.model.interfaces.NotificationDao

open class LocalNotificationRepository private constructor(
    private val notificationDao: NotificationDao
) {
    companion object {
        @Volatile
        private var instance: LocalNotificationRepository? = null
        fun getInstance(notificationDao: NotificationDao): LocalNotificationRepository {
            return instance ?: synchronized(this) {
                instance ?: LocalNotificationRepository(notificationDao).also { instance = it }
            }
        }
    }

    open fun getAllNotifications(): List<NotificationEntity> {
        return notificationDao.getAllNotifications()
    }

    open fun addNotification(notification: NotificationEntity) {
        return notificationDao.addNotification(notification)
    }
}
