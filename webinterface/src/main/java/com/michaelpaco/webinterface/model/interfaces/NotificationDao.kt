package com.michaelpaco.webinterface.model.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.michaelpaco.webinterface.model.data.NotificationEntity

@Dao
interface NotificationDao {
    @Query("SELECT * FROM Notification ORDER BY display_time")
    fun getAll(): List<NotificationEntity>

    @Insert
    fun add(notification: NotificationEntity)

    @Delete
    fun delete(notification: NotificationEntity)
}
