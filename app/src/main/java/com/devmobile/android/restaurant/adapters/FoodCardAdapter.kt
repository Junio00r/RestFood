package com.devmobile.android.restaurant.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.ColorFilter
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
import com.google.android.material.drawable.DrawableUtils

class FoodCardAdapter(

    private val foods: ArrayList<Food>,
    private val context: Context

) : RecyclerView.Adapter<FoodCardViewHolder>(), ClickNotification {
    private var clickNotification: ClickNotification? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardViewHolder {
        val inflater = LayoutInflater.from(context)
        val foodViewInflated = inflater.inflate(R.layout.food_card_layout, parent, false)

        return FoodCardViewHolder(foodViewInflated)
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: FoodCardViewHolder, position: Int) {

        if (foods.size > 0) {

            holder.imageFood.setImageResource(foods[position].mImageId)
            holder.imageFood.scaleType = ImageView.ScaleType.CENTER_CROP
            holder.textFoodName.text = foods[position].mName
            holder.textTimeToPrepare.text = foods[position].mSection.getFoodSectionName()
            holder.checkboxToSelectFood.setOnClickListener {
                hasCheckboxSelected(holder)
            }
//            holder.textFoodDescription.text = foods[position].mDescription

            if (foods[position].mTimeToPrepare == TempoPreparo.LENTO) {

                holder.imageTimeToPrepare.setImageResource(R.drawable.ic_time_prepare_lento)
                holder.textTimeToPrepare.text =
                    "${TempoPreparo.LENTO.getTimeOfPrepareMinutes()} minutos"

            } else if (foods[position].mTimeToPrepare == TempoPreparo.NORMAL) {

                holder.imageTimeToPrepare.setImageResource(R.drawable.ic_time_prepare_normal)
                holder.textTimeToPrepare.text =
                    "${TempoPreparo.NORMAL.getTimeOfPrepareMinutes()} minutos"

            } else if (foods[position].mTimeToPrepare == TempoPreparo.RAPIDO) {

                holder.imageTimeToPrepare.setImageResource(R.drawable.ic_time_prepare_rapido)
                holder.textTimeToPrepare.text =
                    "${TempoPreparo.RAPIDO.getTimeOfPrepareMinutes()} minutos"
            }
        }
    }

    override fun getItemCount(): Int = foods.size

    fun setClickNotifyBridge(clickNotification: ClickNotification) {
        if (this.clickNotification == null) {

            this.clickNotification = clickNotification
        }
    }

    override fun hasCheckboxSelected(v: FoodCardViewHolder) {

        if (v.isCheckboxSelected) {

            modifyCheckBoxButton(v, true)
            v.isCheckboxSelected = false
        } else {

            modifyCheckBoxButton(v, false)
            clickNotification?.hasCheckboxSelected(v)
            v.isCheckboxSelected = true
        }

    }

    @SuppressLint("ResourceType")
    private fun modifyCheckBoxButton(v: FoodCardViewHolder, isCheckedboxCheckable: Boolean) {

        if (isCheckedboxCheckable) {

            val customIconDrawable: Drawable = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.ic_increment,
                null
            ) as Drawable
            v.checkboxToSelectFood.apply {
                background = null
                buttonDrawable = customIconDrawable
                val corFiltro = Color.parseColor("#FF0000")
                buttonDrawable!!.colorFilter = PorterDuffColorFilter(
                    context.getColor(R.color.primary_color),
                    PorterDuff.Mode.SRC_IN
                )
            }
        } else {

            val customIconDrawable: Drawable = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.ic_decrement,
                null
            ) as Drawable
            val customButtonDrawable: Drawable = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.bg_checkbox,
                null
            ) as Drawable
            v.checkboxToSelectFood.apply {
                background = customButtonDrawable
                buttonDrawable = customIconDrawable
                val corFiltro = Color.parseColor("#FF0000")
                buttonDrawable!!.colorFilter = PorterDuffColorFilter(
                    context.getColor(R.color.thirdary_color),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }
    }
}