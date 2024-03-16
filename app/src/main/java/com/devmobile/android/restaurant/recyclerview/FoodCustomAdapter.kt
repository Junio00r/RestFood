package com.devmobile.android.restaurant.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.R
import java.util.LinkedList

class FoodCustomAdapter(

    private val foods: LinkedList<Food>,
    private val context: Context

) : RecyclerView.Adapter<FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        val inflater = LayoutInflater.from(context)
        val foodViewInflated = inflater.inflate(R.layout.food_layout, parent, false)

        return FoodViewHolder(foodViewInflated)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.imageFood.setImageResource(foods[position].imageFoodId)
        holder.imageFood.scaleType = ImageView.ScaleType.FIT_XY
        holder.textFoodName.text = foods[position].foodName
        holder.textFoodDescription.text = foods[position].descriptionFood
    }

    override fun getItemCount(): Int = foods.size
}