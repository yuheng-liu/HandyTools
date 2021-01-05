package com.liuyuheng.handytools.repository

class BillCalculatorRepo {
    
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
