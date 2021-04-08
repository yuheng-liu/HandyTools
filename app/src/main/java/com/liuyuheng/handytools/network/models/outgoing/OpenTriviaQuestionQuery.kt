package com.liuyuheng.handytools.network.models.outgoing

import com.liuyuheng.handytools.repository.TriviaCategory
import com.liuyuheng.handytools.repository.TriviaDifficulty
import com.liuyuheng.handytools.repository.TriviaType

data class OpenTriviaQuestionQuery(
    val category: TriviaCategory? = null,
    val amount: Int = 1,
    val difficulty: TriviaDifficulty? = null,
    val type: TriviaType? = null,
    val token: String
)