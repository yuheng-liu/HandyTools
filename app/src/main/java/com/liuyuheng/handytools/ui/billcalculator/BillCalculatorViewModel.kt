package com.liuyuheng.handytools.ui.billcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.liuyuheng.handytools.repository.Bill
import com.liuyuheng.handytools.repository.BillCalculatorRepo
import com.liuyuheng.handytools.repository.BillPerson
import com.liuyuheng.handytools.ui.AddBillPaymentDialogFragmentState

class BillCalculatorViewModel(
    private val billCalculatorRepo: BillCalculatorRepo
): ViewModel() {

    var addBillPaymentDialogFragmentState: AddBillPaymentDialogFragmentState
        get() = billCalculatorRepo.addBillPaymentDialogFragmentState
        set(value) { billCalculatorRepo.addBillPaymentDialogFragmentState = value }

    var currentBillIndex: Int
        get() = billCalculatorRepo.currentBillIndex
        set(value) { billCalculatorRepo.currentBillIndex = value }

    fun setPersonsString(string: String) = billCalculatorRepo.setPersonsString(string)
    fun updateBill(bill: Bill) = billCalculatorRepo.updateBill(bill)
    fun addEditBillPerson(billPerson: BillPerson) = billCalculatorRepo.addEditBillPerson(billPerson)
    fun deleteCurrentBillPerson(billPerson: BillPerson) = billCalculatorRepo.deleteBillPerson(billPerson)
    fun addBill(billName: String, totalCosts: Double) = billCalculatorRepo.addNewBill(billName, totalCosts)

    fun getPersonsStringLiveData() = billCalculatorRepo.getPersonsStringFlow().asLiveData(timeoutInMs = 0L)
    fun getAllBillListLiveData() = billCalculatorRepo.getAllBillListFlow().asLiveData(timeoutInMs = 0L)
    fun getCurrentBillLiveData() = billCalculatorRepo.getCurrentBillFlow().asLiveData(timeoutInMs = 0L)
}