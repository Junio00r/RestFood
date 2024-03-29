package com.devmobile.android.restaurant

import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder

interface CheckboxClickListener {
    fun hasBeenCheckboxChecked(v: FoodCardViewHolder, isCheckboxChecked: Boolean)
}