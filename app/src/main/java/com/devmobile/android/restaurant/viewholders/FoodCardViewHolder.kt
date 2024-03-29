package com.devmobile.android.restaurant.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.R
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class FoodCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imageFood: ShapeableImageView
    var textFoodName: MaterialTextView
    var imageTimeForPrepare: ShapeableImageView
    var textTimeForPrepare: MaterialTextView
    var checkboxForSelectFood: MaterialCheckBox
    var isCheckboxChecked = false

    init {

        imageFood = view.findViewById(R.id.imageFood)
        textFoodName = view.findViewById(R.id.textFoodName)
        imageTimeForPrepare = view.findViewById(R.id.imageTimeToPrepare)
        textTimeForPrepare = view.findViewById(R.id.textPrepareTime)
        checkboxForSelectFood = view.findViewById(R.id.checkboxFood)
    }
}