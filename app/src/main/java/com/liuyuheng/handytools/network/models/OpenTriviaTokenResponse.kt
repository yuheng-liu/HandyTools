package com.liuyuheng.handytools.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenTriviaTokenResponse(
    @Json(name = "response_code") val code: Int,
    @Json(name = "response_message") val message: String = "",
    @Json(name = "token") val token: String
)
