package com.devmobile.android.restaurant.usecases.extensions

import android.text.InputFilter
import android.widget.TextView

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
