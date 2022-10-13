package com.michaelpaco.webinterface.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.michaelpaco.webinterface.model.data.LifecycleEventEntity
import com.michaelpaco.webinterface.model.interfaces.LifecycleEventDao
import com.michaelpaco.webinterface.util.Const.LIFECYCLE_EVENT_DATABASE_NAME

@Database(entities = [LifecycleEventEntity::class], version = 1, exportSchema = false)
abstract class LifecycleEventDatabase : RoomDatabase() {
    abstract fun lifecycleEventDao(): LifecycleEventDao

    companion object {
        @Volatile
        private var instance: LifecycleEventDatabase? = null

        fun getDatabase(context: Context): LifecycleEventDatabase {
            return instance ?: synchronized(this) {
                val dbInstance = Room.databaseBuilder(context.applicationContext, LifecycleEventDatabase::class.java, LIFECYCLE_EVENT_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()

                instance = dbInstance
                dbInstance
            }
        }
    }
}
