package com.liuyuheng.handytools.ui.trivia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.liuyuheng.handytools.repository.TriviaDifficulty
import com.liuyuheng.handytools.repository.TriviaRepo
import com.liuyuheng.handytools.repository.TriviaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration

class TriviaViewModel(private val triviaRepo: TriviaRepo): ViewModel() {

    fun getTriviaQuestionListLiveData() = triviaRepo.getTriviaQuestionListFlow().asLiveData(timeout = Duration.ZERO)
    fun getTriviaCategoryListLiveData() = triviaRepo.getTriviaCategoryListFlow().asLiveData(timeout = Duration.ZERO)

    fun getTriviaCategory() = liveData(timeoutInMs = 0L) {
        emit(triviaRepo.getTriviaCategories())
    }

    fun getCategoryQuestions(category: String, amount: Int, difficulty: TriviaDifficulty?, type: TriviaType?) = viewModelScope.launch(Dispatchers.IO) {
        triviaRepo.getCategoryQuestions(category, amount, difficulty, type)
    }

    fun getTriviaToken() = viewModelScope.launch(Dispatchers.IO) { triviaRepo.getTriviaToken() }
    fun resetTriviaToken() = viewModelScope.launch(Dispatchers.IO) { triviaRepo.resetTriviaToken() }
    fun deleteTriviaToken() = viewModelScope.launch(Dispatchers.IO) { triviaRepo.deleteTriviaToken() }
}