package com.liuyuheng.handytools.ui.billcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.liuyuheng.handytools.repository.BillItem
import com.liuyuheng.handytools.repository.BillCalculatorRepo
import com.liuyuheng.handytools.repository.BillItemPerson
import com.liuyuheng.handytools.ui.AddItemPaymentDialogFragmentState

class BillCalculatorViewModel(
    private val billCalculatorRepo: BillCalculatorRepo
): ViewModel() {

    var addItemPaymentDialogFragmentState: AddItemPaymentDialogFragmentState
        get() = billCalculatorRepo.addItemPaymentDialogFragmentState
        set(value) { billCalculatorRepo.addItemPaymentDialogFragmentState = value }

    var currentItemIndex: Int
        get() = billCalculatorRepo.currentItemIndex
        set(value) { billCalculatorRepo.currentItemIndex = value }

    fun setPersonsString(string: String) = billCalculatorRepo.setPersonNames(string)
    fun filterExistingBillPersons(nameList: List<String>) = billCalculatorRepo.filterExistingBillPersons(nameList)
    fun addBillItem(itemName: String, itemCost: Double) = billCalculatorRepo.addNewBillItem(itemName, itemCost)
    fun updateBillItem(billItem: BillItem) = billCalculatorRepo.updateBillItem(billItem)
    fun deleteBillItem(itemIndex: Int) = billCalculatorRepo.deleteBillItem(itemIndex)
    fun addEditItemPerson(billItemPerson: BillItemPerson) = billCalculatorRepo.addEditItemPerson(billItemPerson)
    fun deleteCurrentItemPerson(billItemPerson: BillItemPerson) = billCalculatorRepo.deleteBillItemPerson(billItemPerson)

    fun getNameListLiveData() = billCalculatorRepo.getNameListFlow().asLiveData(timeoutInMs = 0L)
    fun getAllBillItemListLiveData() = billCalculatorRepo.getAllBillListFlow().asLiveData(timeoutInMs = 0L)
    fun getCurrentBillItemLiveData() = billCalculatorRepo.getCurrentBillFlow().asLiveData(timeoutInMs = 0L)

    fun getBillSplittingResult() = billCalculatorRepo.getBillSplittingResult()
}