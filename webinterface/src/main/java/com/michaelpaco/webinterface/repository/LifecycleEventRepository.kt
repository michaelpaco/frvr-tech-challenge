package com.michaelpaco.webinterface.repository

import com.michaelpaco.webinterface.model.data.LifecycleEventEntity
import com.michaelpaco.webinterface.model.interfaces.LifecycleEventDao

open class LifecycleEventRepository private constructor(
    private val lifecycleEventDao: LifecycleEventDao
) {
    companion object {
        @Volatile
        private var instance: LifecycleEventRepository? = null
        fun getInstance(lifecycleEventDao: LifecycleEventDao): LifecycleEventRepository {
            return instance ?: synchronized(this) {
                instance ?: LifecycleEventRepository(lifecycleEventDao).also { instance = it }
            }
        }
    }

    open fun getAll(): List<LifecycleEventEntity> {
        return lifecycleEventDao.getAll()
    }

    open fun add(lifecycleEvent: LifecycleEventEntity) {
        return lifecycleEventDao.add(lifecycleEvent)
    }

    open fun deleteOldEvents() {
        return lifecycleEventDao.deleteOldEvents()
    }
}
