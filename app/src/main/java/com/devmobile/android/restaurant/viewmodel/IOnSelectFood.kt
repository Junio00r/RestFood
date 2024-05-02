package com.devmobile.android.restaurant.viewmodel

import com.devmobile.android.restaurant.viewmodel.enums.FoodSection

interface IOnSelectFood {

    fun onAddedFood(
        foodId: Long,
        foodName: String? = null,
        foodPrice: Float? = null,
        sectionOnSelectedFoodOrdinal: FoodSection? = null,
        quantityAdded: Int? = null,
    )

    fun onRemoveFood(
        foodId: Long,
        sectionOnSelectedFood: FoodSection? = null
    )
}