package com.liuyuheng.common.utils.dialogs

import android.content.Context
import android.view.View

object BillCalculatorDialogUtils: BaseDialogUtils() {

    fun getAddEditPersonsDialog(context: Context, view: View) = createCustomAlertDialog(context, view = view)

}