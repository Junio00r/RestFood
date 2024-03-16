package com.devmobile.android.restaurant

data class Food(
    val foodName: String,
    val foodSection: FoodSection,
    val imageFoodId: Int,
    val descriptionFood: String? = null
)
