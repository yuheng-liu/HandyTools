package com.liuyuheng.handytools.ui

import com.liuyuheng.handytools.repository.BillPerson

sealed class AddBillPaymentDialogFragmentState {
    object AddBillPerson: AddBillPaymentDialogFragmentState()
    data class EditBillPerson(val billPerson: BillPerson): AddBillPaymentDialogFragmentState()
}

sealed class BillDetailsFragmentState {
    object AddBill: BillDetailsFragmentState()
    object EditBill: BillDetailsFragmentState()
}