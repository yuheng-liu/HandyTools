package com.liuyuheng.handytools.repository

import com.liuyuheng.common.extensions.round
import com.liuyuheng.handytools.ui.AddBillPaymentDialogFragmentState
import com.liuyuheng.handytools.ui.BillDetailsFragmentState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class BillCalculatorRepo {

    // local variables for sharing among fragments
    var addBillPaymentDialogFragmentState: AddBillPaymentDialogFragmentState = AddBillPaymentDialogFragmentState.AddBillPerson
    var billDetailsFragmentState: BillDetailsFragmentState = BillDetailsFragmentState.AddBill
    var currentBillIndex: Int = 0

    // Persons string for displaying
    private val personsStringFlow = MutableStateFlow("")
    fun getPersonsStringFlow() = personsStringFlow.asStateFlow()
    fun setPersonsString(string: String) { personsStringFlow.value = string }

    // Bills stored in a list for display in recycler view
    private val billsList = mutableListOf<Bill>()
    private val billsListFlow = MutableSharedFlow<List<Bill>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST).apply { tryEmit(billsList) }
    fun getAllBillListFlow() = billsListFlow.asSharedFlow()
    fun getCurrentBillFlow() = getAllBillListFlow().map { billsList -> billsList[currentBillIndex]}
    fun getCurrentBill() = billsListFlow.replayCache[0][currentBillIndex]

    // New Bill for storing temporary data before adding to bills list
    private val newBillFlow = MutableSharedFlow<Bill>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST).apply { tryEmit(Bill()) }
    fun getNewBillFlow() = newBillFlow.asSharedFlow()

    fun addEditBill(bill: Bill) {
        when (billDetailsFragmentState) {
            is BillDetailsFragmentState.AddBill -> billsListFlow.tryEmit(billsList.apply { add(bill) })
            is BillDetailsFragmentState.EditBill -> billsListFlow.tryEmit(billsList.apply { set(currentBillIndex, bill) })
        }
    }

    fun addEditBillPerson(billPerson: BillPerson) {
        when (billDetailsFragmentState) {
            is BillDetailsFragmentState.AddBill -> {
                newBillFlow.tryEmit(newBillFlow.replayCache[0].copy(billPersonList = newBillFlow.replayCache[0].billPersonList.apply {
                    if (any { it.name == billPerson.name }) set(indexOfFirst { it.name == billPerson.name }, billPerson)
                    else add(billPerson)
                }))
            }
            is BillDetailsFragmentState.EditBill -> {
                billsList[currentBillIndex].billPersonList.apply {
                    if (any { it.name == billPerson.name }) set(indexOfFirst { it.name == billPerson.name }, billPerson)
                    else add(billPerson)
                }
                billsListFlow.tryEmit(billsList)
            }
        }
    }

    fun deleteBillPerson(billPerson: BillPerson) {
        when (billDetailsFragmentState) {
            is BillDetailsFragmentState.AddBill -> {
                newBillFlow.tryEmit(newBillFlow.replayCache[0].copy(billPersonList = newBillFlow.replayCache[0].billPersonList.apply {
                    find { it.name == billPerson.name }?.let { remove(it) }
                }))
            }
            is BillDetailsFragmentState.EditBill -> {
                billsList[currentBillIndex].billPersonList.apply {
                    find { it.name == billPerson.name }?.let { remove(it) }
                }
                billsListFlow.tryEmit(billsList)
            }
        }
    }

    fun updateNewBillInfo(billName: String, totalCosts: Double) {
//        billName?.run { newBillFlow.tryEmit(newBillFlow.replayCache[0].copy(name = billName)) }
//        totalCosts?.run { newBillFlow.tryEmit(newBillFlow.replayCache[0].copy(totalCosts = totalCosts)) }
        newBillFlow.tryEmit(newBillFlow.replayCache[0].copy(name = billName, totalCosts = totalCosts))
    }
}

data class Bill(
    val name: String = "",
    val totalCosts: Double = 0.0,
    val billPersonList: MutableList<BillPerson> = mutableListOf()
) {
    fun getTotalCostsString() = "$${String.format("%.2f", totalCosts.round(2))}"
}

data class BillPerson(
    val name: String = "",
    val paidAmount: Double = 0.0
) {
    fun getPaidAmountString() = "$${String.format("%.2f", paidAmount.round(2))}"
}
