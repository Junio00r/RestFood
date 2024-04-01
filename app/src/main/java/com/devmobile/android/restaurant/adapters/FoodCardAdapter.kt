package com.devmobile.android.restaurant.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.CheckboxClickListener
import com.devmobile.android.restaurant.DecimalNumberFormatted
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.enums.TempoPreparo
import com.google.android.material.checkbox.MaterialCheckBox

class FoodCardAdapter(

    private val foods: ArrayList<Food>, private val context: Context

) : RecyclerView.Adapter<FoodCardViewHolder>(), CheckboxClickListener {

    private var checkboxClickListener: CheckboxClickListener? = null
    private val foodCardViewHolders = ArrayList<FoodCardViewHolder>()

    // RecyclerView.Adapter Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardViewHolder {

        val inflater = LayoutInflater.from(context)
        val foodViewInflated = inflater.inflate(R.layout.food_card_layout, parent, false)

        return FoodCardViewHolder(foodViewInflated)
    }

    override fun getItemCount(): Int {

        return foods.size
    }

    override fun onBindViewHolder(holder: FoodCardViewHolder, position: Int) {

        val currentFood = foods[position]

        holder.foodId = currentFood.mId

        // Set CardViewHolder specifications
        holder.imageFood.setImageResource(currentFood.mImageId)
        holder.imageFood.scaleType = ImageView.ScaleType.CENTER_CROP

        holder.textFoodName.text = currentFood.mName
        holder.textFoodDescriptions = currentFood.mDescription.toString()

        val formattedPrice = DecimalNumberFormatted.format(currentFood.mFoodPrice)
        holder.textFoodPrice.text = "R$ $formattedPrice"

        holder.textTimeForPrepare.text = currentFood.mSection.getFoodSectionName()

        holder.checkboxForSelectFood.setOnCheckedChangeListener { _, _ ->
            isCheckboxChecked(holder, false)
        }

        // Set icon time for prepare a food
        when (currentFood.mTimeToPrepare) {

            TempoPreparo.LENTO -> {
                holder.imageTimeForPrepare.setImageResource(R.drawable.ic_time_prepare_lento)
                holder.textTimeForPrepare.text =
                    "${TempoPreparo.LENTO.getTimeOfPrepareMinutes()} minutos"
            }

            TempoPreparo.NORMAL -> {

                holder.imageTimeForPrepare.setImageResource(R.drawable.ic_time_prepare_normal)
                holder.textTimeForPrepare.text =
                    "${TempoPreparo.NORMAL.getTimeOfPrepareMinutes()} minutos"
            }

            TempoPreparo.RAPIDO -> {

                holder.imageTimeForPrepare.setImageResource(R.drawable.ic_time_prepare_rapido)
                holder.textTimeForPrepare.text =
                    "${TempoPreparo.RAPIDO.getTimeOfPrepareMinutes()} minutos"
            }
        }
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
    fun addCheckboxClickListener(checkboxClickListenerOfTabSection: CheckboxClickListener) {

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
                context.getColor(R.color.primary_color), PorterDuff.Mode.SRC_IN
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
                context.getColor(R.color.thirdary_color), PorterDuff.Mode.SRC_IN
            )
        }
    }

}