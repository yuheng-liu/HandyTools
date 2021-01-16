package com.liuyuheng.handytools.ui.billcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.liuyuheng.handytools.repository.BillCalculatorRepo

class BillCalculatorViewModel(
    private val billCalculatorRepo: BillCalculatorRepo
): ViewModel() {

    fun setPersonsList(persons: String) = billCalculatorRepo.setPersonsList(persons.split(", "))

    fun getPersonsList() = billCalculatorRepo.getPersonsListFlow().asLiveData(timeoutInMs = 0L)
}