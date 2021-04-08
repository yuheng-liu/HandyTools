package com.liuyuheng.handytools.storage.preferences

import android.content.Context
import com.liuyuheng.common.utils.BasePreferences

private const val TRIVIA_TOKEN_KEY = "trivia_token_key"

class OpenTrivialSharedPreference(context: Context): BasePreferences(context) {

    fun saveTriviaToken(token: String) = saveString(TRIVIA_TOKEN_KEY, token)
    fun loadTriviaToken() = loadString(TRIVIA_TOKEN_KEY, "")
    fun loadTriviaTokenLiveData() = loadStringLiveData(TRIVIA_TOKEN_KEY, "")
    fun deleteTriviaToken() = deletePreferences(TRIVIA_TOKEN_KEY)
}