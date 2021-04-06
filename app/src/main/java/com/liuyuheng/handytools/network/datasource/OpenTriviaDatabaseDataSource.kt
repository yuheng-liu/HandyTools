package com.liuyuheng.handytools.network.datasource

import com.liuyuheng.handytools.network.retrofit.RetrofitServiceBuilder

class OpenTriviaDatabaseDataSource(retrofitServiceBuilder: RetrofitServiceBuilder): BaseDataSource() {

    private val openTriviaDatabaseApiService = retrofitServiceBuilder.getOpenTriviaDatabaseApiService()

    suspend fun getCategoriesAndIds() = safeApiCall { openTriviaDatabaseApiService.downloadCategoriesAndIds() }
    suspend fun getCategoryQuestions(id: Int, amount: Int, difficulty: String, type: String) = safeApiCall { openTriviaDatabaseApiService.downloadTriviaQuestions(id, amount, difficulty, type) }
    suspend fun getCategoryQuestionCount(id: Int) = safeApiCall { openTriviaDatabaseApiService.downloadCategoryQuestionCount(id) }
}