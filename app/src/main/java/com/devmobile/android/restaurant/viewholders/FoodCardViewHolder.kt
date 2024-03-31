package com.devmobile.android.restaurant.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.R
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class FoodCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var foodId: Long? = 0
    var imageFood: ShapeableImageView
    var textFoodName: MaterialTextView
    var textFoodPrice: MaterialTextView
    var imageTimeForPrepare: ShapeableImageView
    var textTimeForPrepare: MaterialTextView
    var checkboxForSelectFood: MaterialCheckBox
    var isCheckboxChecked = false

    init {

        imageFood = view.findViewById(R.id.imageFood)
        textFoodName = view.findViewById(R.id.textFoodName)
        textFoodPrice = view.findViewById(R.id.textFoodPrice)
        imageTimeForPrepare = view.findViewById(R.id.imageTimeToPrepare)
        textTimeForPrepare = view.findViewById(R.id.textPrepareTime)
        checkboxForSelectFood = view.findViewById(R.id.checkboxFood)
    }
}