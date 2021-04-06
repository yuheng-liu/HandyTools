package com.liuyuheng.handytools.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenTriviaQuestionsResponse(
    @Json(name = "response_code") val code: Int,
    @Json(name = "results") val results: List<OpenTriviaQuestionResults>
) {
    @JsonClass(generateAdapter = true)
    data class OpenTriviaQuestionResults(
        @Json(name = "category") val category: String,
        @Json(name = "type") val type: String,
        @Json(name = "difficulty") val difficulty: String,
        @Json(name = "question") val question: String,
        @Json(name = "correct_answer") val correctAnswer: String,
        @Json(name = "incorrect_answers") val wrongAnswers: List<String>
    )
}