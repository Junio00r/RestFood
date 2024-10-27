package com.devmobile.android.restaurant

interface IOnSelectFood {

    fun onAddedFood(
        foodId: Long,
        foodName: String? = null,
        foodPrice: Float? = null,
        sectionOnSelectedFoodOrdinal: String? = null,
        quantityAdded: Int? = null,
    )

    fun onRemoveFood(
        foodId: Long,
        sectionOnSelectedFood: String? = null
    )
}