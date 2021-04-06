package com.liuyuheng.handytools

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.liuyuheng.handytools.network.retrofit.interceptor.NetworkActivityInterceptor
import java.time.Duration

class MainActivitySharedViewModel(private val networkActivityInterceptor: NetworkActivityInterceptor): ViewModel() {

    fun getApiStateLiveData() = networkActivityInterceptor.getApiCallStateFlow().asLiveData(timeout = Duration.ZERO)
}