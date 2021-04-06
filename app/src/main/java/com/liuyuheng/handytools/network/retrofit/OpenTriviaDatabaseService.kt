package com.liuyuheng.handytools.network.retrofit

import com.liuyuheng.handytools.network.models.OpenTriviaCategoryAndIdResponse
import com.liuyuheng.handytools.network.models.OpenTriviaCategoryQuestionCountResponse
import com.liuyuheng.handytools.network.models.OpenTriviaQuestionsResponse
import com.liuyuheng.handytools.repository.TriviaDifficulty
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTriviaDatabaseApiService {

    @GET("api_category.php")
    suspend fun downloadCategoriesAndIds(): Response<OpenTriviaCategoryAndIdResponse>

    @GET("api_count.php")
    suspend fun downloadCategoryQuestionCount(
        @Query("category") id: Int
    ): Response<OpenTriviaCategoryQuestionCountResponse>

    @GET("api.php")
    suspend fun downloadTriviaQuestions(
        @Query("category") categoryId: Int,
        @Query("amount") amount: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): Response<OpenTriviaQuestionsResponse>

//    @GET("api_token.php")
//    suspend fun downloadSessionToken(
//        @Query("command") command: String,
//        @Query("token") token: String? = null
//    ): Response<>
}