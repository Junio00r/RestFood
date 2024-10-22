package com.devmobile.android.restaurant.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.devmobile.android.restaurant.R

class RestaurantItemList(
    val startIcon: Int? = null,
    val startAction: Int? = null,
    val restaurantName: String,
    val endDrawable: Int? = null,
    val endAction: Int? = null,
) {
    companion object ACTIONS {
        const val CLEAR_FROM_HISTORIC = 0
        const val CLICK = 1
        const val NONE = 2
    }
}

class RestaurantAdapter(

    private val dataSet: List<RestaurantItemList>,
    private val onItemClick: (Int, String) -> Unit

) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(itemView: View) : ViewHolder(itemView) {

        val startResource: ImageView = itemView.findViewById(R.id.start_widget)
        val textView: TextView = itemView.findViewById(R.id.text_restaurant_name)
        val endResource: ImageView = itemView.findViewById(R.id.end_widget)

        fun bindObservable(
            onItemClick: (Int, String) -> Unit,
            restaurantItem: RestaurantItemList
        ) {

            startResource.setOnClickListener {

                onItemClick(restaurantItem.startAction ?: RestaurantItemList.CLICK, restaurantItem.restaurantName)
            }

            endResource.setOnClickListener {

                onItemClick(restaurantItem.endAction ?: RestaurantItemList.CLICK, restaurantItem.restaurantName)
            }

            textView.setOnClickListener {

                onItemClick(RestaurantItemList.CLICK, restaurantItem.restaurantName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_restaurant_item, parent, false)

        return RestaurantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {

        dataSet[position].startIcon?.let { holder.startResource.setBackgroundResource(it) }
        holder.textView.text = dataSet[position].restaurantName
        dataSet[position].endDrawable?.let { holder.endResource.setBackgroundResource(it) }

        holder.bindObservable(onItemClick = onItemClick, restaurantItem = dataSet[position])
    }

    override fun getItemCount(): Int {

        return dataSet.size
    }
}