package com.devmobile.android.restaurant

import com.devmobile.android.restaurant.view.viewholders.FoodCardViewHolder

interface IOnCheckCheckbox {

    fun isCheckboxChecked(v: FoodCardViewHolder, isCheckboxChecked: Boolean)
}