package com.fisherman.common.utils

import android.content.Context
import android.text.Annotation
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import kotlin.math.roundToInt

fun getThemeColor(context: Context, id: Int): Int {
    val typedValue = TypedValue()
    context.theme?.resolveAttribute(id, typedValue, true)
    return typedValue.data
}

fun hoursToMinute(hours: Int): Int {
    return (hours * 60).toDouble().roundToInt()
}

// for modifying portions of text to clickable links, using xml annotations to prevent breaking after localizations
// requires the string to be modified in strings.xml to be enclosed in <annotation> tags
// parameter "links" is a Pair<> with the key of the annotation of the portion to modify, along with an onClickListener
fun generateTextLinks(originalText: SpannedString, textView: TextView, vararg links: Pair<String, View.OnClickListener>) {
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
    textView.movementMethod = LinkMovementMethod.getInstance()
    textView.setText(spannableString, TextView.BufferType.SPANNABLE)
}