package com.liuyuheng.handytools.internal

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log

class NetworkConnectivityUtils(context: Context) {

    private var connectivityManager: ConnectivityManager = context.applicationContext.getSystemService(
        Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    var isWifiConnected = false
    var isCellularConnected = false

    init {
        connectivityManager.registerNetworkCallback(getNetworkRequest(), getNetworkCallback())
    }

    private fun getNetworkRequest() = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private fun getNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) { updateConnectionState(network, "onLost") }
        override fun onUnavailable() { Log.d("ConnectivityUtils","onUnavailable") }
        override fun onLosing(network: Network, maxMsToLive: Int) { updateConnectionState(network, "onLosing")}
        override fun onAvailable(network: Network) { updateConnectionState(network, "onAvailable")}
    }

    private fun updateConnectionState(network: Network, callbackType: String) {
        connectivityManager.getNetworkCapabilities(network)?.let { networkCapabilities ->
            isWifiConnected = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            isCellularConnected = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }

        Log.d("myDebug","Network Connectivity state changed -> $callbackType :: wifi: $isWifiConnected, cellular: $isCellularConnected")
    }
}