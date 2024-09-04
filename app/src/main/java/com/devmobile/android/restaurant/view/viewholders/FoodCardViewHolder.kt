package com.devmobile.android.restaurant.view.viewholders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.model.entities.Food
import com.devmobile.android.restaurant.model.enums.TempoPreparo
import com.devmobile.android.restaurant.ZoneNumberFormat
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

class FoodCardViewHolder(foodCardView: View) : RecyclerView.ViewHolder(foodCardView) {

    var foodId: Long? = 0
    var imageFood: ShapeableImageView = foodCardView.findViewById(R.id.imageFood)
    var textFoodName: MaterialTextView = foodCardView.findViewById(R.id.textFoodName)
    var textFoodPrice: MaterialTextView = foodCardView.findViewById(R.id.textFoodPrice)
    var imageTimeForPrepare: ShapeableImageView = foodCardView.findViewById(R.id.imageTimeToPrepare)
    var textTimeForPrepare: MaterialTextView = foodCardView.findViewById(R.id.textPrepareTime)
    var checkboxForSelectFood: MaterialCheckBox = foodCardView.findViewById(R.id.checkboxFood)
    var isCheckboxChecked = false
    lateinit var textFoodDescriptions: String

    fun setDataOfFoodCard(food: Food) {
        foodId = food.mId

        // Set CardViewHolder specifications
        imageFood.setImageResource(food.mImageId)
        imageFood.scaleType = ImageView.ScaleType.CENTER_CROP

        textFoodName.text = food.mName
        textFoodDescriptions = food.mDescription.toString()

        val formattedPrice = ZoneNumberFormat.format(food.mFoodPrice)
        textFoodPrice.text = "R$ $formattedPrice"

        textTimeForPrepare.text = food.mSection.getFoodSectionName()

//          checkboxForSelectFood.setOnCheckedChangeListener { _, _ ->
//              isCheckboxChecked(holder, false)
//          }

        // Set icon time for prepare a food
        when (food.mTimeToPrepare) {

            TempoPreparo.LENTO -> {
                imageTimeForPrepare.setImageResource(R.drawable.ic_time_prepare_lento)
                textTimeForPrepare.text = "${TempoPreparo.LENTO.getTimeOfPrepareMinutes()} minutos"
            }

            TempoPreparo.NORMAL -> {


                imageTimeForPrepare.setImageResource(R.drawable.ic_time_prepare_normal)

                textTimeForPrepare.text = "${TempoPreparo.NORMAL.getTimeOfPrepareMinutes()} minutos"
            }

            TempoPreparo.RAPIDO -> {

                imageTimeForPrepare.setImageResource(R.drawable.ic_time_prepare_rapido)
                textTimeForPrepare.text = "${TempoPreparo.RAPIDO.getTimeOfPrepareMinutes()} minutos"
            }
        }
    }
}