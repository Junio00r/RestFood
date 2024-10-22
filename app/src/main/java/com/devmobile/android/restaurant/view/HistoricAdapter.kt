package com.devmobile.android.restaurant.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.R
import com.google.android.material.imageview.ShapeableImageView

// This classes will be along with chain classes as FetcherAdapter
// This just for practices

data class HistoricItem(
    val imageId: Int, val name: String
)

class HistoricAdapter(
    private val historicList: List<HistoricItem>?,
    private val onClick: (Unit) -> Unit
) : RecyclerView.Adapter<HistoricAdapter.RestaurantHistoricViewHolder>() {

    class RestaurantHistoricViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ShapeableImageView = itemView.findViewById(R.id.image_restaurant)
        val name: TextView = itemView.findViewById(R.id.text_restaurant_name)

        fun bindObservable(onClick: (Unit) -> Unit) {
            itemView.setOnClickListener {
                onClick(Unit)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RestaurantHistoricViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fast_acess_item_historic, parent, false)

        return RestaurantHistoricViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantHistoricViewHolder, position: Int) {

        historicList?.get(position)?.let { holder.image.setBackgroundResource(it.imageId) }
        holder.name.text = historicList?.get(position)?.name

        holder.bindObservable(onClick)
    }

    override fun getItemCount(): Int {
        return historicList?.size ?: 0
    }
}