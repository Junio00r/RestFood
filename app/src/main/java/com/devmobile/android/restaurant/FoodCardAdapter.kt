package com.devmobile.android.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.util.LinkedList

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

            holder.imageFood.setImageResource(foods[position].imageFoodId)
            holder.imageFood.scaleType = ImageView.ScaleType.FIT_XY
            holder.textFoodName.text = foods[position].foodName
            holder.textTimeToPrepare.text = foods[position].foodSection.getFoodSectionName()
            holder.textFoodDescription.text = foods[position].descriptionFood
        }
    }

    override fun getItemCount(): Int = foods.size
}