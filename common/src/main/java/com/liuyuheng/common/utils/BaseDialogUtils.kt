package com.liuyuheng.common.utils

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseDialogUtils {

    fun createDefaultAlertDialog(context: Context, title: String? = null, message: String? = null,
                                      positiveText: String? = null, positiveFunction: (() -> Unit)? = null,
                                      negativeText: String? = null, negativeFunction: (() -> Unit)? = null,
                                      cancellable: Boolean = true): AlertDialog {

        val builder = MaterialAlertDialogBuilder(context)
        with(builder) {
            title?.run { setTitle(title) }
            message?.run { setMessage(message) }
            positiveText?.run { setPositiveButton(positiveText) { _, _ -> positiveFunction?.invoke() } }
            negativeText?.run { setNegativeButton(negativeText) { _, _ -> negativeFunction?.invoke() } }
            setCancelable(cancellable)
        }
        return builder.create()
    }

    fun createCustomAlertDialog(context: Context, title: String? = null, message: String? = null, view: View,
                                     positiveText: String? = null, positiveFunction: (() -> Unit)? = null,
                                     negativeText: String? = null, negativeFunction: (() -> Unit)? = null,
                                     cancellable: Boolean = true): AlertDialog {

        val builder = MaterialAlertDialogBuilder(context)
        with(builder) {
            title?.run { setTitle(title) }
            message?.run { setMessage(message) }
            setView(view)
            positiveText?.run { setPositiveButton(positiveText) { _, _ -> positiveFunction?.invoke() } }
            negativeText?.run { setNegativeButton(negativeText) { _, _ -> negativeFunction?.invoke() } }
            setCancelable(cancellable)
        }
        return builder.create()
    }
}