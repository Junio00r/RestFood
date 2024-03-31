package com.devmobile.android.restaurant

import com.devmobile.android.restaurant.enums.FoodSection

interface FoodSelectedCallback {

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