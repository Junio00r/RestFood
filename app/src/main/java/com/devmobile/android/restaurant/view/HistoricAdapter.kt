package com.devmobile.android.restaurant.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.devmobile.android.restaurant.R
import com.google.android.material.imageview.ShapeableImageView


// This classes will be along with chain classes as FetcherAdapter
// This just for practices

data class HistoricItem(
    val imageId: Int, val name: String
)

class HistoricAdapter(
    private val dataSet: List<HistoricItem>,
    private val onClick: () -> Unit
) : RecyclerView.Adapter<HistoricAdapter.RestaurantHistoricViewHolder>() {

    class RestaurantHistoricViewHolder(itemView: View) : ViewHolder(itemView) {

        val image: ShapeableImageView = itemView.findViewById(R.id.image_restaurant)
        val name: TextView = itemView.findViewById(R.id.restaurant_name)

        fun bindObservable(onClick: () -> Unit) {
            itemView.setOnClickListener {
                onClick()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RestaurantHistoricViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fast_acess_item_historic, parent, false)
        val result = RestaurantHistoricViewHolder(itemView)

        return result
    }

    override fun onBindViewHolder(holder: RestaurantHistoricViewHolder, position: Int) {

        holder.image.setImageResource(dataSet[position].imageId)
        holder.name.text = dataSet[position].name

        holder.bindObservable(onClick)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}