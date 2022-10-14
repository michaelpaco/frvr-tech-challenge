package com.michaelpaco.webinterface.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.michaelpaco.webinterface.MyApplication
import com.michaelpaco.webinterface.model.data.LifecycleEventEntity
import com.michaelpaco.webinterface.model.database.LifecycleEventDatabase
import com.michaelpaco.webinterface.repository.LifecycleEventRepository

class ActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    private var activitiesOnHold = 0
    private var isChangingConfigurations = false

    private val lifecycleEventsRepository =
        LifecycleEventRepository.getInstance(
            LifecycleEventDatabase.getDatabase(MyApplication.application()).lifecycleEventDao()
        )

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        addEvent("onActivityCreated")
    }

    override fun onActivityDestroyed(activity: Activity) {
        addEvent("onActivityDestroyed")
    }

    override fun onActivityPaused(activity: Activity) {
        addEvent("onActivityPaused")
    }

    override fun onActivityResumed(activity: Activity) {
        addEvent("onActivityResumed")
    }

    override fun onActivitySaveInstanceState(
        activity: Activity,
        bundle: Bundle
    ) {
        addEvent("onActivitySaveInstanceState")
    }

    override fun onActivityStarted(activity: Activity) {
        if (++activitiesOnHold == 1 && !isChangingConfigurations) {
            addEvent("onAppForeground")
        }

        addEvent("onActivityStarted")
    }

    override fun onActivityStopped(activity: Activity) {
        isChangingConfigurations = activity.isChangingConfigurations

        if (--activitiesOnHold == 0 && !isChangingConfigurations) {
            addEvent("onAppBackground")
        }

        addEvent("onActivityStopped")
    }

    private fun addEvent(eventName: String) {
        lifecycleEventsRepository.add(
            LifecycleEventEntity(eventName = eventName, createdAt = System.currentTimeMillis())
        )

        lifecycleEventsRepository.deleteOldEvents()
    }
}
