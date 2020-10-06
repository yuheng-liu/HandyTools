package com.liuyuheng.common.extensions

import android.app.Activity
import android.text.*
import android.text.Annotation
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.liuyuheng.common.internal.GlideApp

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun AlertDialog.showIfHidden() {
    if (!this.isShowing) show()
}

fun AlertDialog.hideIfShown() {
    if (this.isShowing) hide()
}

fun Activity.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

// for modifying portions of text to clickable links, using xml annotations to prevent breaking after localizations
// requires the string to be modified in strings.xml to be enclosed in <annotation> tags
// parameter is a Pair<> with the key of the annotation of the portion to modify, along with an onClickListener
fun TextView.generateLinks(vararg links: Pair<String, View.OnClickListener>) {
    val originalText = this.text as SpannedString
    val annotations = originalText.getSpans(0, originalText.length, Annotation::class.java)
    val spannableString = SpannableString(originalText)

    for (link in links) {
        for (annotation in annotations) {
            if (annotation.value == link.first) {
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        link.second.onClick(view)
                    }
                }
                spannableString.setSpan(
                    clickableSpan,
                    originalText.getSpanStart(annotation),
                    originalText.getSpanEnd(annotation),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }
    this.movementMethod = LinkMovementMethod.getInstance()
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun TextView.setTextOrHide(textToSet: CharSequence?, visibilityWhenEmpty: Int = View.GONE) {
    if (TextUtils.isEmpty(textToSet)) {
        visibility = visibilityWhenEmpty
        text = null
    } else {
        visibility = View.VISIBLE
        text = textToSet
    }
}

fun ImageView.loadWithGlide(imageId: Int) {
    GlideApp.with(context)
        .load(imageId)
        .into(this)
}