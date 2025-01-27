package com.devmobile.android.restaurant.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.devmobile.android.restaurant.databinding.FastAcessItemHistoricBinding
import com.google.android.material.imageview.ShapeableImageView


// This classes will be along with chain classes as FetcherAdapter
// This just for practices
data class HistoricItem(
    val imageId: Int, val name: String
)

class HistoricAdapter(
    private val dataSet: List<HistoricItem>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<HistoricAdapter.RestaurantHistoricViewHolder>() {

    class RestaurantHistoricViewHolder(binding: FastAcessItemHistoricBinding) :
        ViewHolder(binding.root) {

        val image: ShapeableImageView = binding.imageRestaurant
        val name: TextView = binding.restaurantName

        fun bindObservable(onClick: (String) -> Unit) {
            itemView.setOnClickListener {
                onClick(name.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RestaurantHistoricViewHolder {

        val itemBinding = FastAcessItemHistoricBinding.inflate(LayoutInflater.from(parent.context))

        return RestaurantHistoricViewHolder(itemBinding)
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