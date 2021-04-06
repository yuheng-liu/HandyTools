package com.liuyuheng.handytools.network.retrofit.interceptor

import com.liuyuheng.handytools.internal.NetworkConnectivityUtils
import com.liuyuheng.handytools.internal.NoConnectivityException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkActivityInterceptor(private val networkConnectivityUtils: NetworkConnectivityUtils): Interceptor {

    private val apiCallStateFlow = MutableStateFlow<ApiCallState>(ApiCallState.Idle)
    fun getApiCallStateFlow() = apiCallStateFlow.asStateFlow()

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()) throw NoConnectivityException()

        try {
            // set state to waiting before making the request
            apiCallStateFlow.value = ApiCallState.Waiting
            // make the request
            val response = chain.proceed(chain.request())
            // set state to completed after the request returns
            apiCallStateFlow.value = ApiCallState.Completed

            return response
        } catch (e: IOException) {
            apiCallStateFlow.value = ApiCallState.Completed
            throw IOException(e)
        }
    }

    private fun isOnline(): Boolean {
        return networkConnectivityUtils.isWifiConnected || networkConnectivityUtils.isCellularConnected
    }
}

sealed class ApiCallState{
    object Idle: ApiCallState()
    object Waiting: ApiCallState()
    object Completed: ApiCallState()
}