package com.liuyuheng.handytools.internal

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message = "No Internet Connection"
}