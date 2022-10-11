package com.michaelpaco.webinterface.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notification")
data class NotificationEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "display_time") val displayTime: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "message") val message: String
)
