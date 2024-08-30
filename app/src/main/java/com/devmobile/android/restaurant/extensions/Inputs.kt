package com.devmobile.android.restaurant.extensions

import android.os.Parcelable
import android.text.InputFilter
import android.util.SparseArray
import android.view.ViewGroup
import android.widget.TextView
import kotlin.math.max

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
