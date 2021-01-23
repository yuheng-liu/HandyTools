package com.liuyuheng.handytools.repository

import com.liuyuheng.common.extensions.round
import com.liuyuheng.handytools.ui.billcalculator.AddBillPaymentDialogFragment
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class BillCalculatorRepo {

    // local variables for sharing among fragments
    var addBillPaymentDialogFragmentState: AddBillPaymentDialogFragment.Companion.FragmentState = AddBillPaymentDialogFragment.Companion.FragmentState.AddBillPerson
    var currentBillNumber = 0

    // Persons string for displaying
    private val personsStringFlow = MutableStateFlow("")
    fun getPersonsStringFlow() = personsStringFlow.asStateFlow()
    fun setPersonsString(string: String) { personsStringFlow.value = string }

    // Persons as BillPerson stored in a list for displaying in recycler view
    private val personsList = mutableListOf<BillPerson>()
    private val personsListFlow = MutableSharedFlow<List<BillPerson>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST).apply { tryEmit(personsList) }
    fun getPersonsListFlow() = personsListFlow.asSharedFlow()

    // Bills stored in a list for display in recycler view
    private val billsList = mutableListOf<Bill>()
    private val billsListFlow = MutableSharedFlow<List<Bill>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST).apply { tryEmit(billsList) }
    fun getBillsListFlow() = billsListFlow.asSharedFlow()

    fun addEditBillPerson(billPerson: BillPerson) {
        personsListFlow.tryEmit(personsList.apply {
            if (any { it.name == billPerson.name }) set(indexOfFirst { it.name == billPerson.name }, billPerson)
            else add(billPerson)
        })
    }

    fun deleteBillPerson(billPerson: BillPerson) {
        personsListFlow.tryEmit(personsList.apply {
            find { it.name == billPerson.name }?.let { targetBillPerson ->
                remove(targetBillPerson)
            }
        })
    }

    fun addBill(bill: Bill) {
        billsListFlow.tryEmit(billsList.apply {
            if (any { it.name == bill.name }) set(indexOfFirst { it.name == bill.name }, bill)
            else add(bill)
        })
    }
}

data class Bill(
    val name: String,
    val totalCosts: Double,
    val paymentList: List<BillPerson>
) {
    fun getTotalCostsString() = "$${String.format("%.2f", totalCosts.round(2))}"
}

data class BillPerson(
    val name: String = "",
    val paidAmount: Double = 0.0
) {
    fun getPaidAmountString() = "$${String.format("%.2f", paidAmount.round(2))}"
}
