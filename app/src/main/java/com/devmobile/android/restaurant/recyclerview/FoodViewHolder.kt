package com.devmobile.android.restaurant.recyclerview

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.FoodSection
import com.devmobile.android.restaurant.R
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val imageFood: ShapeableImageView
    val textFoodName: MaterialTextView
    val textFoodDescription: MaterialTextView
    lateinit var foodSectionName: FoodSection
    private val imageTimeToPrepare: ShapeableImageView
    var textTimeToPrepare: MaterialTextView
    private val checkSelectFood: MaterialCheckBox

    init {

        imageFood = view.findViewById(R.id.imageFood)
        textFoodName = view.findViewById(R.id.textFoodName)
        textFoodDescription = view.findViewById(R.id.textFoodDescription)
        imageTimeToPrepare = view.findViewById(R.id.imageTimeToPrepare)
        textTimeToPrepare = view.findViewById(R.id.textPrepareTime)
        checkSelectFood = view.findViewById(R.id.checkboxFood)

        setTextsFoodSpecifications()
        setImagesFoodSpecifications()
    }

    private fun setImagesFoodSpecifications() {
    }

    private fun setTextsFoodSpecifications() {

        textFoodName.isSingleLine = true
        textFoodName.textSize = 20f
    }

    override fun onClick(v: View) {

        Toast.makeText(v.context, "Teste", Toast.LENGTH_SHORT).show()
    }
}