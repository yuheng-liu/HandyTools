package com.liuyuheng.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

// live data extension to remove the observer once it is consumed once
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this).replace("am", "AM").replace("pm", "PM")
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.00
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}