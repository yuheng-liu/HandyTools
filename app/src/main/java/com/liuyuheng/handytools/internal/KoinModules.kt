package com.liuyuheng.handytools.internal

import android.content.Context
import com.liuyuheng.handytools.MainActivitySharedViewModel
import com.liuyuheng.handytools.network.datasource.OpenTriviaDatabaseDataSource
import com.liuyuheng.handytools.network.retrofit.RetrofitServiceBuilder
import com.liuyuheng.handytools.network.retrofit.interceptor.NetworkActivityInterceptor
import com.liuyuheng.handytools.repository.BillCalculatorRepo
import com.liuyuheng.handytools.repository.TriviaRepo
import com.liuyuheng.handytools.storage.preferences.OpenTrivialSharedPreference
import com.liuyuheng.handytools.ui.billcalculator.BillCalculatorViewModel
import com.liuyuheng.handytools.ui.trivia.TriviaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object KoinModules {

    fun initKoin(context: Context) = startKoin {
        // Android context
        androidContext(context)
        // modules
        modules(listOf(uiModules, repoModules, networkModules, storageModules))
    }

    private val uiModules = module {
        viewModel { BillCalculatorViewModel(get()) }
        viewModel { MainActivitySharedViewModel(get()) }
        viewModel { TriviaViewModel(get()) }
    }
    
    private val repoModules = module {
        single { BillCalculatorRepo() }
        single { TriviaRepo(get(), get()) }
    }

    private val networkModules = module {
        single { NetworkConnectivityUtils(get()) }
        single { RetrofitServiceBuilder(get()) }
        single { NetworkActivityInterceptor(get()) }
        single { OpenTriviaDatabaseDataSource(get()) }
    }

    private val storageModules = module {
        single { OpenTrivialSharedPreference(get()) }
    }
}