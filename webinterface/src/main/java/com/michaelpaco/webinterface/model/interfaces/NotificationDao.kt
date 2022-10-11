package com.michaelpaco.webinterface.model.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.michaelpaco.webinterface.model.data.NotificationEntity

@Dao
interface NotificationDao {
    @Query("SELECT * FROM Notification ORDER BY display_time")
    fun getAllNotifications(): List<NotificationEntity>

    @Insert
    fun addNotification(notification: NotificationEntity)
}
