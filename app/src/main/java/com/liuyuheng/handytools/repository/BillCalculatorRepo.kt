package com.liuyuheng.handytools.repository

import com.liuyuheng.common.extensions.round
import com.liuyuheng.handytools.ui.AddBillPaymentDialogFragmentState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class BillCalculatorRepo {

    // local variables for sharing among fragments
    var addBillPaymentDialogFragmentState: AddBillPaymentDialogFragmentState = AddBillPaymentDialogFragmentState.AddBillPerson
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

    fun addNewBill(billName: String, totalCosts: Double) {
        billsListFlow.tryEmit(billsList.apply { add(Bill(billName, totalCosts)) })
    }

    fun updateBill(updatedBill: Bill) {
        billsListFlow.tryEmit(billsList.apply { set(currentBillIndex, updatedBill) })
    }

    fun addEditBillPerson(billPerson: BillPerson) {
        val newList = billsListFlow.replayCache[0][currentBillIndex].billPersonList.toMutableList().apply {
            if (any { it.name == billPerson.name }) set(indexOfFirst { it.name == billPerson.name }, billPerson)
            else add(billPerson)
        }
        billsListFlow.tryEmit(billsList.apply { this[currentBillIndex].billPersonList = newList })
    }

    fun deleteBillPerson(billPerson: BillPerson) {
        val newList = billsListFlow.replayCache[0][currentBillIndex].billPersonList.toMutableList().apply {
            find { it.name == billPerson.name }?.let { remove(it) }
        }
        billsListFlow.tryEmit(billsList.apply { this[currentBillIndex].billPersonList = newList })
    }
}

data class Bill(
    val name: String = "",
    val totalCosts: Double = 0.0,
    var billPersonList: List<BillPerson> = emptyList()
) {
    fun getTotalCostsString(withDollarPrefix: Boolean = true): String {
        val prefix = if(withDollarPrefix) "$" else ""
        return "$prefix${String.format("%.2f", totalCosts.round(2))}"
    }
}

data class BillPerson(
    val name: String = "",
    val paidAmount: Double = 0.0
) {
    fun getPaidAmountString(withDollarPrefix: Boolean = true): String {
        val prefix = if(withDollarPrefix) "$" else ""
        return "$prefix${String.format("%.2f", paidAmount.round(2))}"
    }
}
