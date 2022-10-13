package com.michaelpaco.webinterface.model.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.michaelpaco.webinterface.model.data.LifecycleEventEntity

@Dao
interface LifecycleEventDao {
    @Query("SELECT * FROM LifecycleEvent ORDER BY created_at ASC")
    fun getAll(): List<LifecycleEventEntity>

    @Insert
    fun add(lifecycleEvent: LifecycleEventEntity)

    @Query("DELETE FROM LifecycleEvent WHERE id IN (SELECT id FROM LifecycleEvent ORDER BY created_at DESC LIMIT 1 OFFSET 20)")
    fun deleteOldEvents()
}
