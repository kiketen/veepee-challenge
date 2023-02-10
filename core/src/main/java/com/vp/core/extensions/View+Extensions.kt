package com.vp.core.extensions

import android.view.View
import android.view.ViewTreeObserver

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.switchVisibility(visible: Boolean) {
    when (visible) {
        true -> visible()
        false -> gone()
    }
}
