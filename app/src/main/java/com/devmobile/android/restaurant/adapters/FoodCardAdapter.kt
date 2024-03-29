package com.devmobile.android.restaurant.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.ClickNotification
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.enums.TempoPreparo
import com.google.android.material.checkbox.MaterialCheckBox
import java.util.LinkedList

class FoodCardAdapter(

    private val foods: ArrayList<Food>, private val context: Context

) : RecyclerView.Adapter<FoodCardViewHolder>(), ClickNotification {

    private var clickNotification: ClickNotification? = null
    private val foodCardViewHolders = LinkedList<FoodCardViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardViewHolder {

        val inflater = LayoutInflater.from(context)
        val foodViewInflated = inflater.inflate(R.layout.food_card_layout, parent, false)

        return FoodCardViewHolder(foodViewInflated)
    }

    override fun onBindViewHolder(holder: FoodCardViewHolder, position: Int) {

        if (foods.size > 0) {

            holder.imageFood.setImageResource(foods[position].mImageId)
            holder.imageFood.scaleType = ImageView.ScaleType.CENTER_CROP
            holder.textFoodName.text = foods[position].mName
            holder.textTimeForPrepare.text = foods[position].mSection.getFoodSectionName()
            holder.checkboxForSelectFood.setOnClickListener {
                hasBeenCheckboxChecked(holder, false)
            }

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
    }

    fun getFoodCardViewHoldersSelected(): LinkedList<FoodCardViewHolder>? {

        foodCardViewHolders.all { foodViewHolder ->
            foodViewHolder.isCheckboxChecked
            return foodCardViewHolders
        }

        return null
    }

    override fun getItemCount(): Int {

        return foods.size
    }

    fun setClickNotifyBridge(clickNotification: ClickNotification) {

        if (this.clickNotification == null)
            this.clickNotification = clickNotification
    }

    override fun hasBeenCheckboxChecked(v: FoodCardViewHolder, isCheckboxChecked: Boolean) {

        if (v.isCheckboxChecked) {

            setButtonCheckboxUnchecked(v.checkboxForSelectFood)
            clickNotification?.hasBeenCheckboxChecked(v, false)
            v.isCheckboxChecked = false

        } else {

            if (!foodCardViewHolders.contains(v)) {
                foodCardViewHolders.add(v)
            }

            setButtonCheckboxChecked(v.checkboxForSelectFood)
            clickNotification?.hasBeenCheckboxChecked(v, true)
            v.isCheckboxChecked = true
        }
    }

    private fun setButtonCheckboxUnchecked(checkboxChecked: MaterialCheckBox) {


        val customIconDrawable: Drawable = ResourcesCompat.getDrawable(
            context.resources, R.drawable.ic_increment, null
        ) as Drawable

        checkboxChecked.apply {
            background = null
            buttonDrawable = customIconDrawable
            val corFiltro = Color.parseColor("#FF0000")
            buttonDrawable!!.colorFilter = PorterDuffColorFilter(
                context.getColor(R.color.primary_color), PorterDuff.Mode.SRC_IN
            )
        }
    }

    private fun setButtonCheckboxChecked(checkboxUnchecked: MaterialCheckBox) {

        val customIconDrawable: Drawable = ResourcesCompat.getDrawable(
            context.resources, R.drawable.ic_decrement, null
        ) as Drawable
        val customButtonDrawable: Drawable = ResourcesCompat.getDrawable(
            context.resources, R.drawable.bg_checkbox, null
        ) as Drawable

        checkboxUnchecked.apply {
            background = customButtonDrawable
            buttonDrawable = customIconDrawable
            val corFiltro = Color.parseColor("#FF0000")
            buttonDrawable!!.colorFilter = PorterDuffColorFilter(
                context.getColor(R.color.thirdary_color), PorterDuff.Mode.SRC_IN
            )
        }
    }
}