package com.devmobile.android.restaurant.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import com.devmobile.android.restaurant.R

class FoodCardAdapter(

    private val foods: ArrayList<Food>,
    private val context: Context

) : RecyclerView.Adapter<FoodCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardViewHolder {

        val inflater = LayoutInflater.from(context)
        val foodViewInflated = inflater.inflate(R.layout.food_card_layout, parent, false)

        return FoodCardViewHolder(foodViewInflated)
    }

    override fun onBindViewHolder(holder: FoodCardViewHolder, position: Int) {

        if (foods.size > 0) {

            holder.imageFood.setImageResource(foods[position].mImageId)
            holder.imageFood.scaleType = ImageView.ScaleType.FIT_XY
            holder.textFoodName.text = foods[position].mName
            holder.textTimeToPrepare.text = foods[position].mSection.getFoodSectionName()
            holder.textFoodDescription.text = foods[position].mDescription
        }
    }

    override fun getItemCount(): Int = foods.size
}