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

    open fun getAll(): List<NotificationEntity> {
        return notificationDao.getAll()
    }

    open fun add(notification: NotificationEntity) {
        return notificationDao.add(notification)
    }

    open fun delete(notification: NotificationEntity) {
        return notificationDao.delete(notification)
    }
}
