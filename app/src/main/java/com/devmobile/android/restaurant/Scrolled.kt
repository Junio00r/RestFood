package com.devmobile.android.restaurant

import android.widget.ScrollView
import androidx.core.view.ScrollingView

interface Scrolled {
    fun hasBeenScrolled(data: ScrollingView, dx: Int, dy: Int)
}