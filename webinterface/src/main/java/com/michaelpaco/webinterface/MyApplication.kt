package com.michaelpaco.webinterface

import android.app.Application
import android.content.Context

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
}
