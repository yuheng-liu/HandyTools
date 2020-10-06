package com.liuyuheng.handytools

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.liuyuheng.handytools.internal.KoinModules

class MainApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        KoinModules.initKoin(this)
    }

    companion object {
        // singleton instance of MainApplication
        lateinit var instance: MainApplication
            private set

        // public variable to allow non activity/fragment classes to get context
        val applicationContext: Context
            get() {
                return instance.applicationContext
            }

        // public variable to allow non activity/fragment classes to get resources
        val applicationResources: Resources
            get() {
                return instance.resources
            }
    }
}