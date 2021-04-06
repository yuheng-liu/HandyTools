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

    fun getTriviaCategoryListLiveData() = triviaRepo.getTriviaCategoryListFlow().asLiveData(timeout = Duration.ZERO)

    init {
        
    }

    fun getTriviaCategory() = liveData(timeoutInMs = 0L) {
        emit(triviaRepo.getTriviaCategories())
    }

    fun getCategoryQuestions(category: String, amount: Int, difficulty: TriviaDifficulty, type: TriviaType) = liveData(timeoutInMs = 0L) {
        emit(triviaRepo.getCategoryQuestions(category, amount, difficulty.query, type.query))
    }
}