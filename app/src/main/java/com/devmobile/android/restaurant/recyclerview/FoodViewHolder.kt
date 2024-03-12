package com.devmobile.android.restaurant.recyclerview

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.R
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    var textViewFood: MaterialTextView
    var imageViewFood: ShapeableImageView
    var checkBox: MaterialCheckBox

    init {

        textViewFood = view.findViewById(R.id.textFoodName)
        imageViewFood = view.findViewById(R.id.imageFood)
        checkBox = view.findViewById(R.id.checkboxFood)
        setTextFoodSpecifications()
        setImageFoodSpecifications()
    }

    private fun setImageFoodSpecifications() {
    }

    private fun setTextFoodSpecifications() {

        textViewFood.isSingleLine = true
        textViewFood.textSize = 20f
    }

    override fun onClick(v: View) {

        Toast.makeText(v.context, "Teste", Toast.LENGTH_SHORT).show()
    }
}