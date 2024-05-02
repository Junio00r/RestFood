package com.devmobile.android.restaurant.viewmodel

import com.devmobile.android.restaurant.viewmodel.viewholders.FoodCardViewHolder

interface IOnCheckCheckbox {

    fun isCheckboxChecked(v: FoodCardViewHolder, isCheckboxChecked: Boolean)
}