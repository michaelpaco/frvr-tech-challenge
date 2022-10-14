package com.michaelpaco.webinterface.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.michaelpaco.webinterface.model.data.NotificationEntity
import com.michaelpaco.webinterface.model.interfaces.NotificationDao
import com.michaelpaco.webinterface.util.Const.NOTIFICATION_DATABASE_NAME

@Database(entities = [NotificationEntity::class], version = 1, exportSchema = false)
abstract class LocalNotificationDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var instance: LocalNotificationDatabase? = null

        fun getDatabase(context: Context): LocalNotificationDatabase {
            return instance ?: synchronized(this) {
                val dbInstance = Room.databaseBuilder(context.applicationContext, LocalNotificationDatabase::class.java, NOTIFICATION_DATABASE_NAME)
                    .build()
                instance = dbInstance
                dbInstance
            }
        }
    }
}
