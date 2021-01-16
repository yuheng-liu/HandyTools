package com.liuyuheng.handytools.internal

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(id: Int) {
    findNavController().navigate(id)
}