package com.devmobile.android.restaurant

import androidx.core.view.ScrollingView

interface Scrolled {

    fun hasBeenScrolled(data: ScrollingView, dx: Int, dy: Int)
}