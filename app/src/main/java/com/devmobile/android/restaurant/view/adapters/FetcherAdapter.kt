package com.devmobile.android.restaurant.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.devmobile.android.restaurant.databinding.SearchRestaurantItemBinding

class RestaurantItemList(
    val startDrawable: Int? = null,
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

    class RestaurantViewHolder(binding: SearchRestaurantItemBinding) : ViewHolder(binding.root) {

        val startResource: ImageView = binding.startWidget
        val textView: TextView = binding.restaurantName
        val endResource: ImageView = binding.endWidget

        fun setObservable(
            onItemClick: (Int, String) -> Unit,
            restaurantItem: RestaurantItemList
        ) {

            startResource.setOnClickListener {

                onItemClick(RestaurantItemList.CLICK, restaurantItem.restaurantName)
            }

            endResource.setOnClickListener {

                onItemClick(
                    restaurantItem.endAction ?: RestaurantItemList.CLICK,
                    restaurantItem.restaurantName
                )
            }

            textView.setOnClickListener {

                onItemClick(RestaurantItemList.CLICK, restaurantItem.restaurantName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {

        val itemBinding = SearchRestaurantItemBinding.inflate(LayoutInflater.from(parent.context))

        return RestaurantViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {

        dataSet[position].startDrawable?.let { holder.startResource.setBackgroundResource(it) }
        holder.textView.text = dataSet[position].restaurantName
        dataSet[position].endDrawable?.let { holder.endResource.setBackgroundResource(it) }

        holder.setObservable(onItemClick = onItemClick, restaurantItem = dataSet[position])
    }

    override fun getItemCount(): Int {

        return dataSet.size
    }
}