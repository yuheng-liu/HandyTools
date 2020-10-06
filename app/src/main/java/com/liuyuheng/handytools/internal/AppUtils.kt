package com.liuyuheng.handytools.internal

import android.content.Context
import com.liuyuheng.handytools.MainApplication.Companion.applicationContext
import com.liuyuheng.handytools.MainApplication.Companion.applicationResources

fun getString(id: Int, vararg formatArgs: String?): String {
    return applicationResources.getString(id, *formatArgs)
}

fun getApplicationContext(): Context {
    return applicationContext
}