package com.liuyuheng.handytools.repository

import com.liuyuheng.common.extensions.round
import com.liuyuheng.handytools.ui.AddItemPaymentDialogFragmentState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlin.math.absoluteValue

class BillCalculatorRepo {

    // local variables for sharing among fragments
    var addItemPaymentDialogFragmentState: AddItemPaymentDialogFragmentState = AddItemPaymentDialogFragmentState.AddItemPerson
    var currentItemIndex: Int = 0

    // Persons string for displaying
    private val nameListFlow = MutableStateFlow(emptyList<String>())
    fun getNameListFlow() = nameListFlow.asStateFlow()

    // Bills stored in a list for display in recycler view
    private val billsList = mutableListOf<BillItem>()
    private val billsListFlow = MutableSharedFlow<List<BillItem>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST).apply { tryEmit(billsList) }
    fun getAllBillListFlow() = billsListFlow.asSharedFlow()
    fun getCurrentBillFlow() = getAllBillListFlow().map { billsList -> billsList[currentItemIndex]}

    fun setPersonNames(string: String) {
        nameListFlow.value = string.split(",").map { it.trim() }
    }

    fun addNewBillItem(billName: String, billCost: Double) {
        billsListFlow.tryEmit(billsList.apply { add(BillItem(billName, billCost)) })
    }

    fun updateBillItem(updatedBillItem: BillItem) {
        billsListFlow.tryEmit(billsList.apply { set(currentItemIndex, updatedBillItem) })
    }

    fun deleteBillItem(billIndex: Int) {
        billsListFlow.tryEmit(billsList.apply { removeAt(billIndex) })
    }

    fun addEditItemPerson(billItemPerson: BillItemPerson) {
        val newList = billsListFlow.replayCache[0][currentItemIndex].itemPersonList.toMutableList().apply {
            if (any { it.name == billItemPerson.name }) set(indexOfFirst { it.name == billItemPerson.name }, billItemPerson)
            else add(billItemPerson)
        }
        billsListFlow.tryEmit(billsList.apply { this[currentItemIndex].itemPersonList = newList })
    }

    fun deleteBillItemPerson(billItemPerson: BillItemPerson) {
        val newList = billsListFlow.replayCache[0][currentItemIndex].itemPersonList.toMutableList().apply {
            find { it.name == billItemPerson.name }?.let { remove(it) }
        }
        billsListFlow.tryEmit(billsList.apply { this[currentItemIndex].itemPersonList = newList })
    }

    fun filterExistingBillPersons(personNames: List<String>): List<String> {
        val currentBillPersonsList = billsList[currentItemIndex].itemPersonList
        return personNames.filterNot { personName -> currentBillPersonsList.any { billPerson -> billPerson.name == personName } }
    }

    /**
     * Full logic of calculating how much each person should pay or receive
     */
    fun getBillSplittingResult(): String {
        val totalCosts = billsList.sumBy { (it.cost * 100).toInt() }
        val numberOfPersons = nameListFlow.value.size
        val costPerPerson = totalCosts.div(numberOfPersons)

        var resultString = ""

        nameListFlow.value.forEach { personName ->
            var cumulativePaid = 0
            billsList.forEach { billItem ->
                billItem.itemPersonList.find { billItemPerson -> billItemPerson.name == personName }?.let { billItemPerson ->
                    cumulativePaid += (billItemPerson.paidAmount * 100).toInt()
                }
            }
            resultString += "$personName ${getResultString(cumulativePaid, costPerPerson)}"
        }

        return resultString
    }

    private fun getResultString(paidAmount: Int, costPerPerson: Int): String {
        val difAmount = paidAmount - costPerPerson

        return when {
            difAmount > 0 -> "receives $${String.format("%.2f", (difAmount.absoluteValue/100.0).round(2))}\n"
            difAmount == 0 -> "breaks even\n"
            difAmount < 0 -> "pays $${String.format("%.2f", (difAmount.absoluteValue/100.0).round(2))}\n"
            else -> ""
        }
    }
}

data class BillItem(
    val name: String = "",
    val cost: Double = 0.0,
    var itemPersonList: List<BillItemPerson> = emptyList()
) {
    fun getItemCostString(withDollarPrefix: Boolean = true): String {
        val prefix = if(withDollarPrefix) "$" else ""
        return "$prefix${String.format("%.2f", cost.round(2))}"
    }
}

data class BillItemPerson(
    val name: String = "",
    val paidAmount: Double = 0.0
) {
    fun getPaidAmountString(withDollarPrefix: Boolean = true): String {
        val prefix = if(withDollarPrefix) "$" else ""
        return "$prefix${String.format("%.2f", paidAmount.round(2))}"
    }
}
