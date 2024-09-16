package com.devmobile.android.restaurant.viewmodel.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.IOnCheckCheckbox
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.view.viewholders.FoodCardViewHolder
import com.devmobile.android.restaurant.R
import com.google.android.material.checkbox.MaterialCheckBox

class FoodCardAdapter(

    private val foods: ArrayList<Food>, private val context: Context

) : RecyclerView.Adapter<FoodCardViewHolder>(), IOnCheckCheckbox {

    private var checkboxClickListener: IOnCheckCheckbox? = null
    private val foodCardViewHolders = ArrayList<FoodCardViewHolder>()

    // RecyclerView.Adapter Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardViewHolder {

        val inflater = LayoutInflater.from(context)
        val foodCardViewInflated = inflater.inflate(R.layout.layout_food_card, parent, false)

        return FoodCardViewHolder(foodCardViewInflated)
    }

    override fun getItemCount(): Int {

        return foods.size
    }

    override fun onBindViewHolder(holder: FoodCardViewHolder, position: Int) {

        holder.setDataOfFoodCard(foods[position])
    }

    fun getFoodCardViewHoldersSelected(): List<FoodCardViewHolder> {

        return foodCardViewHolders.filter { it.isCheckboxChecked }
    }

    fun cancelOrder() {

        getFoodCardViewHoldersSelected().forEach {
            isCheckboxChecked(it, true)
        }
    }

    // Listeners...
    fun addCheckboxClickListener(checkboxClickListenerOfTabSection: IOnCheckCheckbox) {

        if (this.checkboxClickListener == null) {
            this.checkboxClickListener = checkboxClickListenerOfTabSection
        }
    }

    override fun isCheckboxChecked(v: FoodCardViewHolder, isCheckboxChecked: Boolean) {

        v.let {
            if (v.isCheckboxChecked) {

                unCheckCheckbox(v.checkboxForSelectFood)
                checkboxClickListener?.isCheckboxChecked(v, false)
                v.isCheckboxChecked = false

            } else {

                if (!foodCardViewHolders.contains(v)) {
                    foodCardViewHolders.add(v)
                }

                checkCheckbox(v.checkboxForSelectFood)
                checkboxClickListener?.isCheckboxChecked(v, true)
                v.isCheckboxChecked = true
            }
        }
    }

    // Change checkbox State after click it...
    private fun unCheckCheckbox(checkboxChecked: MaterialCheckBox) {

        val incrementIcon: Drawable = ResourcesCompat.getDrawable(
            context.resources, R.drawable.ic_increment, null
        ) as Drawable

        checkboxChecked.apply {

            background = null
            buttonDrawable = incrementIcon
            buttonDrawable!!.colorFilter = PorterDuffColorFilter(
                context.getColor(R.color.black), PorterDuff.Mode.SRC_IN
            )
        }
    }

    private fun checkCheckbox(checkboxUnChecked: MaterialCheckBox) {

        val decrementIcon: Drawable = ResourcesCompat.getDrawable(
            context.resources, R.drawable.ic_decrement, null
        ) as Drawable
        val customCheckboxButton: Drawable = ResourcesCompat.getDrawable(
            context.resources, R.drawable.bg_checkbox, null
        ) as Drawable

        checkboxUnChecked.apply {

            background = customCheckboxButton
            buttonDrawable = decrementIcon
            buttonDrawable!!.colorFilter = PorterDuffColorFilter(
                context.getColor(R.color.white), PorterDuff.Mode.SRC_IN
            )
        }
    }

}