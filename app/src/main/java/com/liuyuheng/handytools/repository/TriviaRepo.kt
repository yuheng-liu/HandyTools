package com.liuyuheng.handytools.repository

import android.util.Log
import androidx.core.text.HtmlCompat
import com.liuyuheng.handytools.network.datasource.DataSourceResult
import com.liuyuheng.handytools.network.datasource.OpenTriviaDatabaseDataSource
import com.liuyuheng.handytools.network.models.outgoing.OpenTriviaQuestionQuery
import com.liuyuheng.handytools.storage.preferences.OpenTrivialSharedPreference
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class TriviaRepo(
    private val openTriviaDatabaseDataSource: OpenTriviaDatabaseDataSource,
    private val trivialSharedPreference: OpenTrivialSharedPreference
) {

    private var triviaCategoryList = emptyList<TriviaCategory>()
    private val triviaCategoryListFlow = MutableSharedFlow<List<TriviaCategory>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST).apply { tryEmit(triviaCategoryList) }
    fun getTriviaCategoryListFlow() = triviaCategoryListFlow.asSharedFlow()

    private val triviaQuestionListFlow = MutableSharedFlow<List<TriviaQuestion>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    fun getTriviaQuestionListFlow() = triviaQuestionListFlow.asSharedFlow()

    private var triviaToken: String
        set(value) = trivialSharedPreference.saveTriviaToken(value)
        get() = trivialSharedPreference.loadTriviaToken()

//    suspend fun getTriviaCategories() {
//        downloadTriviaCategoryAndId()?.let { downloadedTriviaCategoryList ->
//            triviaCategoryList = downloadedTriviaCategoryList
//            triviaCategoryList.forEach { triviaCategory ->
//                triviaCategory.numberOfQuestions = downloadTriviaCategoryQuestionCount(triviaCategory.id)
//            }
//            triviaCategoryListFlow.tryEmit(triviaCategoryList)
//        }
//    }

    suspend fun getTriviaToken() {
        when (val result = openTriviaDatabaseDataSource.getTriviaToken()) {
            is DataSourceResult.Success -> {
                result.data?.let { data ->
                    if (data.code == TriviaResponseCode.Success.code) triviaToken = data.token
                    else Log.d("myDebug", "get trivia token unSuccessful with code: ${TriviaResponseCode.values().find { it.code == data.code }}")
                }
            }
            is DataSourceResult.Failure -> Log.d("myDebug", "getTriviaToken failed")
        }
    }

    suspend fun resetTriviaToken() {
        when (val result = openTriviaDatabaseDataSource.resetTriviaToken(triviaToken)) {
            is DataSourceResult.Success -> {
                result.data?.let { data ->
                    if (data.code == TriviaResponseCode.Success.code) triviaToken = data.token
                    else Log.d("myDebug", "get trivia token unSuccessful with code: ${TriviaResponseCode.values().find { it.code == data.code }}")
                }
            }
            is DataSourceResult.Failure -> Log.d("myDebug", "resetTriviaToken failed")
        }
    }

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

    suspend fun getCategoryQuestions(category: String, amount: Int, difficulty: TriviaDifficulty?, type: TriviaType?) {
        // get token if there's none
        if (triviaToken.isBlank()) getTriviaToken()

        when (val result = openTriviaDatabaseDataSource.getCategoryQuestions(OpenTriviaQuestionQuery(triviaCategoryList.find { it.name == category }, amount, difficulty, type, triviaToken))) {
            is DataSourceResult.Success -> {
                result.data?.let { data ->
                    Log.d("myDebug", "download trivia questions response code: ${TriviaResponseCode.entries.find { it.code == data.code }}")
                    when (data.code) {
                        TriviaResponseCode.Success.code -> {
                            triviaQuestionListFlow.tryEmit(data.results.map { result ->
                                val decodedQuestion = HtmlCompat.fromHtml(result.question, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                                val decodedWrongAnswers = result.wrongAnswers.map { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString() }
                                val decodedCorrectAnswers = HtmlCompat.fromHtml(result.correctAnswer, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                                TriviaQuestion(decodedQuestion, decodedWrongAnswers, decodedCorrectAnswers) }
                            )
                        }
                        TriviaResponseCode.TokenNotFound.code,
                        TriviaResponseCode.InvalidParameter.code -> {
                            getTriviaToken()
                            getCategoryQuestions(category, amount, difficulty, type)
                        }
                        TriviaResponseCode.NoResult.code,
                        TriviaResponseCode.TokenEmpty.code -> {
                            resetTriviaToken()
                            getCategoryQuestions(category, amount, difficulty, type)
                        }
                        else -> Log.d("myDebug", "getCategoryQuestions Invalid response code")
                    }
                }
            }
            is DataSourceResult.Failure -> Log.d("myDebug", "downloadTriviaQuestions failed")
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

    fun deleteTriviaToken() = trivialSharedPreference.deleteTriviaToken()
}

data class TriviaCategory(
    val name: String = "",
    val id: Int = 0,
)

data class TriviaQuestion(
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

enum class TriviaResponseCode(val code: Int) {
    Success(0), NoResult(1), InvalidParameter(2), TokenNotFound(3), TokenEmpty(4)
}