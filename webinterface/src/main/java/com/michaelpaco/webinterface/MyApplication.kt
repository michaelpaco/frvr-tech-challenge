package com.michaelpaco.webinterface

import android.app.Application
import com.michaelpaco.webinterface.util.ActivityLifecycleCallbacks

open class MyApplication : Application() {

    companion object {
        var instance: Application? = null

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
