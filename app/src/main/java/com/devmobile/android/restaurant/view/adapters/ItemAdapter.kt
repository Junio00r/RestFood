package com.devmobile.android.restaurant.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.databinding.LayoutItemBinding
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import kotlin.properties.Delegates

class ItemAdapter(
    private val items: List<Item>,
    private val onAddItem: ((Boolean, Long) -> (Unit))? = null,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val foodImage: ImageView = binding.imageItem
        val foodName: TextView = binding.textItemName
        val foodPrice: TextView = binding.textItemPrice
        val selectItem: Button = binding.buttonSelectItem
        var foodId by Delegates.notNull<Long>()
        private var mustItemAdd = false

        fun setListener(onClick: (Boolean, Long) -> Unit) {

            selectItem.setOnClickListener {

                mustItemAdd = !mustItemAdd
                onClick(mustItemAdd, foodId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val viewBinding =
            LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.foodImage.setImageResource(items[position].imageId)
        holder.foodName.text = items[position].name
        holder.foodPrice.text = items[position].price.toString()
        holder.foodId = items[position].id

        if (onAddItem != null) {
            holder.setListener(onAddItem)
        }
    }

    override fun getItemCount(): Int {

        return items.size
    }
}