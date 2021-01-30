package com.liuyuheng.handytools.ui.billcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.liuyuheng.handytools.repository.Bill
import com.liuyuheng.handytools.repository.BillCalculatorRepo
import com.liuyuheng.handytools.repository.BillPerson
import com.liuyuheng.handytools.ui.AddBillPaymentDialogFragmentState
import com.liuyuheng.handytools.ui.BillDetailsFragmentState

class BillCalculatorViewModel(
    private val billCalculatorRepo: BillCalculatorRepo
): ViewModel() {

    var addBillPaymentDialogFragmentState: AddBillPaymentDialogFragmentState
        get() = billCalculatorRepo.addBillPaymentDialogFragmentState
        set(value) { billCalculatorRepo.addBillPaymentDialogFragmentState = value }

    var billDetailsFragmentState: BillDetailsFragmentState
        get() = billCalculatorRepo.billDetailsFragmentState
        set(value) { billCalculatorRepo.billDetailsFragmentState = value }

    var currentBillIndex: Int
        get() = billCalculatorRepo.currentBillIndex
        set(value) { billCalculatorRepo.currentBillIndex = value }

    fun setPersonsString(string: String) = billCalculatorRepo.setPersonsString(string)
    fun addEditBill(bill: Bill) = billCalculatorRepo.addEditBill(bill)
    fun addEditBillPerson(billPerson: BillPerson) = billCalculatorRepo.addEditBillPerson(billPerson)
    fun deleteCurrentBillPerson(billPerson: BillPerson) = billCalculatorRepo.deleteBillPerson(billPerson)
//    fun updateNewBillName(billName: String) = billCalculatorRepo.updateNewBillInfo(billName = billName)
//    fun updateNewTotalCosts(totalCosts: Double) = billCalculatorRepo.updateNewBillInfo(totalCosts = totalCosts)
    fun updateNewBillInfo(billName: String, totalCosts: Double) = billCalculatorRepo.updateNewBillInfo(billName, totalCosts)

    fun getPersonsStringLiveData() = billCalculatorRepo.getPersonsStringFlow().asLiveData(timeoutInMs = 0L)
    fun getAllBillListLiveData() = billCalculatorRepo.getAllBillListFlow().asLiveData(timeoutInMs = 0L)
    fun getCurrentBillLiveData() = billCalculatorRepo.getCurrentBillFlow().asLiveData(timeoutInMs = 0L)
    fun getNewBillLiveData() = billCalculatorRepo.getNewBillFlow().asLiveData(timeoutInMs = 0L)

    fun isBillAlreadyExists(billName: String) = billCalculatorRepo.getAllBillListFlow().replayCache[0].any { it.name == billName }
    fun isPersonAlreadyExists(personName: String): Boolean {
        return when (billDetailsFragmentState) {
            is BillDetailsFragmentState.AddBill -> billCalculatorRepo.getNewBillFlow().replayCache[0].billPersonList.any { it.name == personName }
            is BillDetailsFragmentState.EditBill -> billCalculatorRepo.getCurrentBill().billPersonList.any { it.name == personName }
        }
    }
}