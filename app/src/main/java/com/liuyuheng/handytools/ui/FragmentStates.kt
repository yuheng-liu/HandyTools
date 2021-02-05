package com.liuyuheng.handytools.ui

import com.liuyuheng.handytools.repository.BillItemPerson

sealed class AddItemPaymentDialogFragmentState {
    object AddItemPerson: AddItemPaymentDialogFragmentState()
    data class EditItemPerson(val billItemPerson: BillItemPerson): AddItemPaymentDialogFragmentState()
}