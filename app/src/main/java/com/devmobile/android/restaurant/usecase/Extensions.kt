package com.devmobile.android.restaurant.usecase

import android.text.InputFilter
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Set the max of characters on this TextView
 * @param maxLength amount character accept
 */
fun TextView.maxLength(maxLength: Int) {

    val currentlyFilters = filters.toMutableList()
    currentlyFilters.removeIf { it is InputFilter.LengthFilter }

    currentlyFilters.add(InputFilter.LengthFilter(maxLength))

    filters = currentlyFilters.toTypedArray()
}

fun debounce() {

}

inline fun <reified T> T.isKeyboardEnabled(): Boolean where T : View {
    val insets = ViewCompat.getRootWindowInsets(this)

    return insets?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
}
