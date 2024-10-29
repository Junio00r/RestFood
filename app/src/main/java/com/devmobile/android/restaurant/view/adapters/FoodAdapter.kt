package com.devmobile.android.restaurant.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.databinding.LayoutFoodBinding
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import kotlin.properties.Delegates

class FoodAdapter(
    private val foods: ArrayList<Food>,
    private val onAddFood: (Boolean) -> (Unit),
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(binding: LayoutFoodBinding) : RecyclerView.ViewHolder(binding.root) {

        val foodImage: ImageView = binding.imageFood
        val foodName: TextView = binding.textFoodName
        val foodPrice: TextView = binding.textFoodPrice
        val addButton: Button = binding.buttonAddFood
        var foodId by Delegates.notNull<Long>()
        private var isFoodAdd = false

        fun setListener(onClick: (Boolean) -> Unit) {

            addButton.setOnClickListener {

                isFoodAdd = !isFoodAdd
                onClick(isFoodAdd)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        val viewBinding =
            LayoutFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FoodViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.foodImage.setImageResource(foods[position].imageId)
        holder.foodName.text = foods[position].name
        holder.foodPrice.text = foods[position].price.toString()
        holder.foodId = foods[position].id
        holder.setListener(onAddFood)
    }

    override fun getItemCount(): Int {

        return foods.size
    }
}