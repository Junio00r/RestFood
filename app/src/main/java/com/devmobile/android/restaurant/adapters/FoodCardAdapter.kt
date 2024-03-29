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
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.enums.TempoPreparo
import com.google.android.material.checkbox.MaterialCheckBox
import java.util.LinkedList

class FoodCardAdapter(

    private val foods: ArrayList<Food>, private val context: Context

) : RecyclerView.Adapter<FoodCardViewHolder>(), CheckboxClickListener {

    private var checkboxClickNotification: CheckboxClickListener? = null
    private val foodCardViewHolders = LinkedList<FoodCardViewHolder>()

    // Métodos of RecyclerView.Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardViewHolder {

        val inflater = LayoutInflater.from(context)
        val foodViewInflated = inflater.inflate(R.layout.food_card_layout, parent, false)

        return FoodCardViewHolder(foodViewInflated)
    }

    override fun onBindViewHolder(holder: FoodCardViewHolder, position: Int) {

        // Set CardViewHolder specifications
        holder.imageFood.setImageResource(foods[position].mImageId)
        holder.imageFood.scaleType = ImageView.ScaleType.CENTER_CROP
        holder.textFoodName.text = foods[position].mName
        holder.textTimeForPrepare.text = foods[position].mSection.getFoodSectionName()
        holder.checkboxForSelectFood.setOnClickListener {
            hasBeenCheckboxChecked(holder, false)
        }

        // Set icon time for prepare a food
        when (foods[position].mTimeToPrepare) {

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

    override fun getItemCount(): Int {

        return foods.size
    }


    fun getFoodCardViewHoldersSelected(): List<FoodCardViewHolder> {

        return foodCardViewHolders.filter { it.isCheckboxChecked }
    }


    fun cancelOrder() {

        getFoodCardViewHoldersSelected().forEach {
            unCheckCheckbox(it.checkboxForSelectFood)
            it.isCheckboxChecked = false
        }
    }


    // Listeners...
    fun addCheckboxClickListener(checkboxClickListenerOfTabSection: CheckboxClickListener) {

        if (this.checkboxClickNotification == null)
            this.checkboxClickNotification = checkboxClickListenerOfTabSection
    }

    override fun hasBeenCheckboxChecked(v: FoodCardViewHolder, isCheckboxChecked: Boolean) {

        if (v.isCheckboxChecked) {

            unCheckCheckbox(v.checkboxForSelectFood)
            checkboxClickNotification?.hasBeenCheckboxChecked(v, false)
            v.isCheckboxChecked = false

        } else {

            if (!foodCardViewHolders.contains(v)) {
                foodCardViewHolders.add(v)
            }

            checkCheckbox(v.checkboxForSelectFood)
            checkboxClickNotification?.hasBeenCheckboxChecked(v, true)
            v.isCheckboxChecked = true
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