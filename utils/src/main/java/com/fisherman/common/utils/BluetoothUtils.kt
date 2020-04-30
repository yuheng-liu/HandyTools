package com.fisherman.common.utils

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BluetoothUtils {

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private val mIsBluetoothEnabledLiveData = MutableLiveData<Boolean>()
    val isBluetoothEnabledLiveData: LiveData<Boolean> = mIsBluetoothEnabledLiveData

    private val bluetoothReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
                    BluetoothAdapter.STATE_ON -> { mIsBluetoothEnabledLiveData.value = true }
                    BluetoothAdapter.STATE_OFF -> { mIsBluetoothEnabledLiveData.value = false }
                }
            }
        }
    }

    fun isBluetoothEnabled() = bluetoothAdapter?.isEnabled ?: false

    fun registerBluetoothBroadcastReceiver(context: Context) {
        context.registerReceiver(bluetoothReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
        mIsBluetoothEnabledLiveData.value = isBluetoothEnabled()
    }

    fun unregisterBluetoothBroadcastReceiver(context: Context) {
        context.unregisterReceiver(bluetoothReceiver)
    }
}