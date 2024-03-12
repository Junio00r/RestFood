package com.devmobile.android.restaurant.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.R
import java.util.LinkedList

class FoodCustomAdapter(

    private val foodImagesId: LinkedList<Int>,
    private val foodNames: LinkedList<String>,
    private val context: Context

) : RecyclerView.Adapter<FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        val inflater = LayoutInflater.from(context)
        val foodViewInflated = inflater.inflate(R.layout.food_layout, parent, false)

        return FoodViewHolder(foodViewInflated)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.textViewFood.text = foodNames[position]
        holder.imageViewFood.scaleType = ImageView.ScaleType.FIT_XY
        holder.imageViewFood.setImageResource(foodImagesId[position])
    }

    override fun getItemCount(): Int = foodNames.size
}