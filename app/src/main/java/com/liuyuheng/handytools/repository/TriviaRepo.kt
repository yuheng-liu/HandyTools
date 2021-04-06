package com.liuyuheng.handytools.repository

import android.util.Log
import com.liuyuheng.handytools.network.datasource.DataSourceResult
import com.liuyuheng.handytools.network.datasource.OpenTriviaDatabaseDataSource
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class TriviaRepo(private val openTriviaDatabaseDataSource: OpenTriviaDatabaseDataSource) {

    private var triviaCategoryList = emptyList<TriviaCategory>()
    private val triviaCategoryListFlow = MutableSharedFlow<List<TriviaCategory>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST).apply { tryEmit(triviaCategoryList) }
    fun getTriviaCategoryListFlow() = triviaCategoryListFlow.asSharedFlow()

//    suspend fun getTriviaCategories() {
//        downloadTriviaCategoryAndId()?.let { downloadedTriviaCategoryList ->
//            triviaCategoryList = downloadedTriviaCategoryList
//            triviaCategoryList.forEach { triviaCategory ->
//                triviaCategory.numberOfQuestions = downloadTriviaCategoryQuestionCount(triviaCategory.id)
//            }
//            triviaCategoryListFlow.tryEmit(triviaCategoryList)
//        }
//    }

    suspend fun getTriviaCategories(): List<TriviaCategory> {
        return when (val result = openTriviaDatabaseDataSource.getCategoriesAndIds()) {
            is DataSourceResult.Success -> {
                result.data?.run {
                    categories.map { TriviaCategory(name = it.name, id = it.id) }.also { triviaCategoryList = it }
                } ?: emptyList()
            }
            is DataSourceResult.Failure -> {
                Log.d("myDebug", "downloadTriviaCategoryAndId failed")
                emptyList()
            }
        }
    }

    suspend fun getCategoryQuestions(category: String, amount: Int, difficulty: String, type: String): List<TriviaQuestions> {
        val triviaCategory = triviaCategoryList.find { it.name == category } ?: TriviaCategory()

        return when (val result = openTriviaDatabaseDataSource.getCategoryQuestions(triviaCategory.id, amount, difficulty, type)) {
            is DataSourceResult.Success -> {
                result.data?.let { data ->
                    Log.d("myDebug", "download trivia questions response code: ${ResponseCode.values().find { it.code == data.code }}")
                    data.results.map { result ->
                        TriviaQuestions(
                            question = result.question,
                            wrongChoices = result.wrongAnswers,
                            answer = result.correctAnswer
                        )
                    }
                } ?: emptyList()
            }
            is DataSourceResult.Failure -> {
                Log.d("myDebug", "downloadTriviaQuestions failed")
                emptyList()
            }
        }
    }

//    private suspend fun downloadTriviaCategoryQuestionCount(id: Int): TriviaCategory.TriviaCategoryQuestionCount {
//        return when (val result = openTriviaDatabaseDataSource.getCategoryQuestionCount(id)) {
//            is DataSourceResult.Success -> {
//                result.data?.run {
//                    TriviaCategory.TriviaCategoryQuestionCount(
//                        totalCount = questionCount.totalQuestionsCount,
//                        easyCount = questionCount.easyQuestionsCount,
//                        mediumCount = questionCount.mediumQuestionsCount,
//                        hardCount = questionCount.hardQuestionsCount
//                    )
//                } ?: TriviaCategory.TriviaCategoryQuestionCount()
//            }
//            is DataSourceResult.Failure -> {
//                Log.d("myDebug", "downloadTriviaCategoryQuestionCount id$id Failed")
//                TriviaCategory.TriviaCategoryQuestionCount()
//            }
//        }
//    }
}

data class TriviaCategory(
    val name: String = "",
    val id: Int = 0,
)

data class TriviaQuestions(
    val question: String = "",
    val wrongChoices: List<String> = emptyList(),
    val answer: String = ""
)

data class TriviaCategoryQuestionCount(
    val totalCount: Int = 0,
    val easyCount: Int = 0,
    val mediumCount: Int = 0,
    val hardCount: Int = 0
)

enum class TriviaDifficulty(val text: String, val query: String) {
    Easy("Easy", "easy"), Medium("Medium", "medium"), Hard("Hard", "hard")
}

enum class TriviaType(val text: String, val query: String) {
    MultipleChoice("Multiple Choice", "multiple"), TrueFalse("True/False", "boolean")
}

enum class ResponseCode(val code: Int) {
    Success(0), NoResults(1), InvalidParameter(2), TokenNotFound(3), TokenEmpty(4)
}