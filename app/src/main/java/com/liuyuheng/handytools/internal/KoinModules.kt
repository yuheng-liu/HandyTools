package com.liuyuheng.handytools.internal

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

object KoinModules {

    fun initKoin(context: Context) = startKoin {
        // Android context
        androidContext(context)
        // modules
        modules(listOf())
    }

    private val uiModules = module {

    }
}