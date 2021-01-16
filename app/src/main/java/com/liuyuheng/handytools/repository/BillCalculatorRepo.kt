package com.liuyuheng.handytools.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BillCalculatorRepo {

    private val personsListFlow = MutableStateFlow(emptyList<BillPerson>())
    fun getPersonsListFlow() = personsListFlow.asStateFlow()

    fun setPersonsList(list: List<String>) {
        personsListFlow.value = list.map { personName -> BillPerson(name = personName) }
    }
}

data class Bill(
    val id: Int,
    val totalPrice: Int,
    val items: List<BillItem>,
    val paidBy: String
) {
    data class BillItem(
        val name: String,
        val price: Int,
        val paidBy: String
    )
}

data class BillPerson(
    val name: String,
    val paidAmount: Double = 0.0
)
