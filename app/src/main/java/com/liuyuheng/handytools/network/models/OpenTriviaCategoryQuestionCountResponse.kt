package com.liuyuheng.handytools.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenTriviaCategoryQuestionCountResponse(
    @Json(name = "category_id") val id: Int,
    @Json(name = "category_question_count") val questionCount: OpenTriviaCategoryQuestionCount
) {
    @JsonClass(generateAdapter = true)
    data class OpenTriviaCategoryQuestionCount(
        @Json(name = "total_question_count") val totalQuestionsCount: Int,
        @Json(name = "total_easy_question_count") val easyQuestionsCount: Int,
        @Json(name = "total_medium_question_count") val mediumQuestionsCount: Int,
        @Json(name = "total_hard_question_count") val hardQuestionsCount: Int
    )
}