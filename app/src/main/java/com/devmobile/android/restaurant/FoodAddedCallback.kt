package com.devmobile.android.restaurant

interface FoodAddedCallback {

    fun onAddedFood(foodPrice: Float, quantityAdded: Int, preferencesFood: String)
}