package com.devmobile.android.restaurant.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.devmobile.android.restaurant.R
import com.google.android.material.imageview.ShapeableImageView

data class RestaurantSearchItem(val name: String, val image: String? = null)

class RestaurantViewHolder(itemView: View) : ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.text_restaurant_name)
    val imageView: ShapeableImageView = itemView.findViewById(R.id.image_restaurant_image)
}

class RestaurantAdapter(private val dataSet: List<RestaurantSearchItem>) : RecyclerView.Adapter<RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_restaurant_item, parent, false)

        return RestaurantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {

        holder.textView.text = dataSet[position].name
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}