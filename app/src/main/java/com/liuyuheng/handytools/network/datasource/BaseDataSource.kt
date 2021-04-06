package com.liuyuheng.handytools.network.datasource

import android.util.Log
import com.liuyuheng.handytools.internal.NoConnectivityException
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import retrofit2.Response
import java.net.UnknownHostException

abstract class BaseDataSource {

    // All API calls should go through this function
    protected suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): DataSourceResult<T> {
        try {
            // get Retrofit Response object from API call
            val response = call()
            // SUCCESS -> return the response body wrapped in Result wrapper
            if (response.isSuccessful) { return DataSourceResult.Success(response.body()) }
            // FAILURE -> return the converted error response body wrapped in Result wrapper
            return DataSourceResult.Failure(null)
        } catch (e: Exception) {
            // catch and handle the error
            handleErrorResponse(e)
            return DataSourceResult.Failure(null)
        }
    }

    // Retrofit returns a ResponseBody class when failed, need to convert to a local model before use
    private fun <T> convertErrorBody(errorBodyJson: String?) : T? {
//        if (errorBodyJson != null) {
//            val moshi = Moshi.Builder().build()
//            val adapter: JsonAdapter<T> = moshi.adapter(T::class.java)
//            return adapter.fromJson(errorBodyJson)
//        }
        return null
    }

    /*
     * Error state handler
     */
    private fun handleErrorResponse(exception: java.lang.Exception) {
        val errorMessage = when (exception) {
            // exceptions to handle explicitly if needed, else will catch all other exceptions
            is NoConnectivityException -> "No Internet Connection"
            is UnknownHostException -> "No address associated with hostname"
            is JsonEncodingException -> "App/Server Jsons did not match"
            is java.io.IOException -> "Api call timeout"
            else -> exception.message ?: exception.toString()
        }
        Log.d("myDebug", "BaseDataSource onError ${exception::class.java}: $errorMessage")
    }
}

sealed class DataSourceResult<out T: Any> {
    data class Success<out T: Any>(val data: T?) : DataSourceResult<T>()
    data class Failure<out T: Any>(val data: T?) : DataSourceResult<T>()
}