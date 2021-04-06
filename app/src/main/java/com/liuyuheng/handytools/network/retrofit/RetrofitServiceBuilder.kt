package com.liuyuheng.handytools.network.retrofit

import com.liuyuheng.handytools.network.retrofit.interceptor.NetworkActivityInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val OPEN_TRIVIAL_DATABASE_BASE_URL = "https://opentdb.com/"

class RetrofitServiceBuilder(networkActivityInterceptor: NetworkActivityInterceptor) {

    fun getOpenTriviaDatabaseApiService(): OpenTriviaDatabaseApiService = getRetrofitInstance(OPEN_TRIVIAL_DATABASE_BASE_URL, defaultClient).create(OpenTriviaDatabaseApiService::class.java)

    // underlying http client used in retrofit instance
    private val defaultClient = OkHttpClient.Builder()
        // for keeping track of start and end of network api call
        .addInterceptor(networkActivityInterceptor)
        // for debugging, adding logs to logcat
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    private fun getRetrofitInstance(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}