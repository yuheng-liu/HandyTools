package com.liuyuheng.handytools.internal

import android.content.Context
import com.liuyuheng.handytools.repository.BillCalculatorRepo
import com.liuyuheng.handytools.ui.billcalculator.BillCalculatorViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object KoinModules {

    fun initKoin(context: Context) = startKoin {
        // Android context
        androidContext(context)
        // modules
        modules(listOf(uiModules, repoModules))
    }

    private val uiModules = module {
        viewModel { BillCalculatorViewModel() }
    }
    
    private val repoModules = module { 
        BillCalculatorRepo()
    }
}