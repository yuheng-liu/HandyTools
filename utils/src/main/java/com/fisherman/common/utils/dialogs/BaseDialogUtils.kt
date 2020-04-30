package com.fisherman.common.utils.dialogs

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseDialogUtils {

    fun createDefaultAlertDialog(context: Context, title: Int? = null, message: Int? = null,
                                      positiveText: Int? = null, positiveFunction: (() -> Unit)? = null,
                                      negativeText: Int? = null, negativeFunction: (() -> Unit)? = null,
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

    fun createCustomAlertDialog(context: Context, title: Int? = null, message: Int? = null, view: View,
                                     positiveText: Int? = null, positiveFunction: (() -> Unit)? = null,
                                     negativeText: Int? = null, negativeFunction: (() -> Unit)? = null,
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