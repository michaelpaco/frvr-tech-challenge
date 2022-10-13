package com.michaelpaco.webinterface.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LifecycleEvent")
data class LifecycleEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "event_name")
    val eventName: String,
    @ColumnInfo(name = "created_at", defaultValue = "(strftime('%s','now','localtime'))")
    val createdAt: Long? = null
)
