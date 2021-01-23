package com.liuyuheng.handytools.ui.billcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.liuyuheng.handytools.repository.Bill
import com.liuyuheng.handytools.repository.BillCalculatorRepo
import com.liuyuheng.handytools.repository.BillPerson

class BillCalculatorViewModel(
    private val billCalculatorRepo: BillCalculatorRepo
): ViewModel() {

    var addBillPaymentDialogFragmentState: AddBillPaymentDialogFragment.Companion.FragmentState
        get() = billCalculatorRepo.addBillPaymentDialogFragmentState
        set(value) { billCalculatorRepo.addBillPaymentDialogFragmentState = value }

    fun setPersonsString(string: String) = billCalculatorRepo.setPersonsString(string)
    fun addEditBillPerson(billPerson: BillPerson) = billCalculatorRepo.addEditBillPerson(billPerson)
    fun addBill(bill: Bill) = billCalculatorRepo.addBill(bill)

    fun getPersonsList() = billCalculatorRepo.getPersonsListFlow().replayCache[0]

    fun getPersonsStringLiveData() = billCalculatorRepo.getPersonsStringFlow().asLiveData(timeoutInMs = 0L)
    fun getPersonsListLiveData() = billCalculatorRepo.getPersonsListFlow().asLiveData(timeoutInMs = 0L)
    fun getBillsListLiveData() = billCalculatorRepo.getBillsListFlow().asLiveData(timeoutInMs = 0L)

    fun isPersonAlreadyExists(personName: String) = billCalculatorRepo.getPersonsListFlow().replayCache[0].any { it.name == personName }
    fun isBillAlreadyExists(billName: String) = billCalculatorRepo.getBillsListFlow().replayCache[0].any { it.name == billName }

    fun deleteCurrentBillPerson(billPerson: BillPerson) = billCalculatorRepo.deleteBillPerson(billPerson)
}