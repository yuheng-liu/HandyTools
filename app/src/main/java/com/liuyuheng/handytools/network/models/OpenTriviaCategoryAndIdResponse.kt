package com.liuyuheng.handytools.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenTriviaCategoryAndIdResponse(
    @Json(name = "trivia_categories") val categories: List<OpenTriviaCategory>
) {
    @JsonClass(generateAdapter = true)
    data class OpenTriviaCategory(
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String
    )
}