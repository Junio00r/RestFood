package com.devmobile.android.restaurant.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.ClickNotification
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.enums.TempoPreparo

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

            holder.imageFood.scaleType = ImageView.ScaleType.FIT_XY
            holder.imageFood.setImageResource(foods[position].mImageId)
            holder.textFoodName.text = foods[position].mName
            holder.textTimeToPrepare.text = foods[position].mSection.getFoodSectionName()
            holder.checkboxToSelectFood.setOnClickListener{
                checkboxClicked(holder)
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

    override fun checkboxClicked(v: FoodCardViewHolder) {
        clickNotification?.checkboxClicked(v)
    }
}