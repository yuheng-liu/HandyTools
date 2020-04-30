package com.fisherman.common.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData

private const val DEFAULT = "DEFAULT"

abstract class BasePreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(DEFAULT, Context.MODE_PRIVATE)

    /*
     *  save to shared preferences
     */
    fun saveInt(key: String, value: Int) = with(sharedPreferences.edit()) {
        putInt(key, value)
        apply()
    }
    fun saveString(key: String, value: String) = with(sharedPreferences.edit()) {
        putString(key, value)
        apply()
    }
    fun saveBoolean(key: String, value: Boolean) = with(sharedPreferences.edit()) {
        putBoolean(key, value)
        apply()
    }
    fun saveFloat(key: String, value: Float) = with(sharedPreferences.edit()) {
        putFloat(key, value)
        apply()
    }
    fun saveLong(key: String, value: Long) = with(sharedPreferences.edit()) {
        putLong(key, value)
        apply()
    }

    /*
     *  load from shared preferences
     */
    fun loadInt(key: String, defValue: Int): Int = sharedPreferences.getInt(key, defValue)
    fun loadInt(key: String, defValue: String): String = sharedPreferences.getString(key, defValue) ?: ""
    fun loadInt(key: String, defValue: Boolean): Boolean = sharedPreferences.getBoolean(key, defValue)
    fun loadInt(key: String, defValue: Float): Float = sharedPreferences.getFloat(key, defValue)
    fun loadInt(key: String, defValue: Long): Long = sharedPreferences.getLong(key, defValue)

    /*
     *  load from shared preference as liveData
     */
    fun loadIntLiveData(key: String, defValue: Int): LiveData<Int> {
        return sharedPreferences.intLiveData(key, defValue)
    }
    fun loadStringLiveData(key: String, defValue: String): LiveData<String> {
        return sharedPreferences.stringLiveData(key, defValue)
    }
    fun loadBooleanLiveData(key: String, defValue: Boolean): LiveData<Boolean> {
        return sharedPreferences.booleanLiveData(key, defValue)
    }
    fun loadFloatLiveData(key: String, defValue: Float): LiveData<Float> {
        return sharedPreferences.floatLiveData(key, defValue)
    }
    fun loadLongLiveData(key: String, defValue: Long): LiveData<Long> {
        return sharedPreferences.longLiveData(key, defValue)
    }

    /*
     *  delete from shared preferences
     */
    fun deletePreferences(key: String) = with(sharedPreferences.edit()) {
        remove(key)
        apply()
    }
}

abstract class SharedPreferenceLiveData<T>(val sharedPrefs: SharedPreferences, private val key: String, private val defValue: T) : LiveData<T>() {

    init {
        value = this.getValueFromPreferences(key, defValue)
    }

    abstract fun getValueFromPreferences(key: String, defValue: T): T

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == this.key) {
            value = getValueFromPreferences(key, defValue)
        }
    }

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}

class SharedPreferenceIntLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Int) :
    SharedPreferenceLiveData<Int>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Int): Int = sharedPrefs.getInt(key, defValue)
}

class SharedPreferenceStringLiveData(sharedPrefs: SharedPreferences, key: String, defValue: String) :
    SharedPreferenceLiveData<String>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: String): String = sharedPrefs.getString(key, defValue) ?: ""
}

class SharedPreferenceBooleanLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Boolean) :
    SharedPreferenceLiveData<Boolean>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Boolean): Boolean = sharedPrefs.getBoolean(key, defValue)
}

class SharedPreferenceFloatLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Float) :
    SharedPreferenceLiveData<Float>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Float): Float = sharedPrefs.getFloat(key, defValue)
}

class SharedPreferenceLongLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Long) :
    SharedPreferenceLiveData<Long>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Long): Long = sharedPrefs.getLong(key, defValue)
}

class SharedPreferenceStringSetLiveData(sharedPrefs: SharedPreferences, key: String, defValue: Set<String>) :
    SharedPreferenceLiveData<Set<String>>(sharedPrefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Set<String>): Set<String> = sharedPrefs.getStringSet(key, defValue) ?: setOf("")
}

fun SharedPreferences.intLiveData(key: String, defValue: Int): SharedPreferenceLiveData<Int> {
    return SharedPreferenceIntLiveData(this, key, defValue)
}

fun SharedPreferences.stringLiveData(key: String, defValue: String): SharedPreferenceLiveData<String> {
    return SharedPreferenceStringLiveData(this, key, defValue)
}

fun SharedPreferences.booleanLiveData(key: String, defValue: Boolean): SharedPreferenceLiveData<Boolean> {
    return SharedPreferenceBooleanLiveData(this, key, defValue)
}

fun SharedPreferences.floatLiveData(key: String, defValue: Float): SharedPreferenceLiveData<Float> {
    return SharedPreferenceFloatLiveData(this, key, defValue)
}

fun SharedPreferences.longLiveData(key: String, defValue: Long): SharedPreferenceLiveData<Long> {
    return SharedPreferenceLongLiveData(this, key, defValue)
}

fun SharedPreferences.stringSetLiveData(key: String, defValue: Set<String>): SharedPreferenceLiveData<Set<String>> {
    return SharedPreferenceStringSetLiveData(this, key, defValue)
}