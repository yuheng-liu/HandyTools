package com.liuyuheng.handytools.network.datasource

import com.liuyuheng.handytools.network.models.outgoing.OpenTriviaQuestionQuery
import com.liuyuheng.handytools.network.retrofit.RetrofitServiceBuilder

class OpenTriviaDatabaseDataSource(retrofitServiceBuilder: RetrofitServiceBuilder): BaseDataSource() {

    private val openTriviaDatabaseApiService = retrofitServiceBuilder.getOpenTriviaDatabaseApiService()

    suspend fun getTriviaToken() = safeApiCall { openTriviaDatabaseApiService.downloadSessionToken(TokenCommand.Request.query) }
    suspend fun resetTriviaToken(token: String) = safeApiCall { openTriviaDatabaseApiService.resetSessionToken(TokenCommand.Reset.query, token) }
    suspend fun getCategoriesAndIds() = safeApiCall { openTriviaDatabaseApiService.downloadCategoriesAndIds() }
    suspend fun getCategoryQuestions(data: OpenTriviaQuestionQuery) = safeApiCall {
        openTriviaDatabaseApiService.downloadTriviaQuestions(data.category?.id, data.amount, data.difficulty?.query, data.type?.query, data.token)
    }
    suspend fun getCategoryQuestionCount(id: Int) = safeApiCall { openTriviaDatabaseApiService.downloadCategoryQuestionCount(id) }
}

enum class TokenCommand(val query: String){
    Request("request"), Reset("reset")
}