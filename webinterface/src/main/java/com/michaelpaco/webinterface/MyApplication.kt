package com.michaelpaco.webinterface

import android.app.Application
import android.content.Context
import com.michaelpaco.webinterface.util.ActivityLifecycleCallbacks

open class MyApplication : Application() {

    companion object {
        var instance: Application? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun application() = instance!!
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks())
    }
}
