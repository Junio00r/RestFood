package com.devmobile.android.restaurant.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.databinding.LayoutButtonCustomElementBinding
import com.devmobile.android.restaurant.view.customelements.CustomItemView
import com.devmobile.android.restaurant.view.customelements.CustomItemView.Companion.ITEM_TYPE
import com.devmobile.android.restaurant.view.customelements.CustomItemView.Companion.UNSPECIFIED
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.ItemBetweenUiAndVM

class ComplementaryItemsAdapter(
    private val context: Context,
    private var complementaryItems: List<ItemBetweenUiAndVM>,
    private val bindClick: ((Boolean) -> Unit),
) : RecyclerView.Adapter<ComplementaryItemsAdapter.ComplementaryItemVH>() {

    private val requiredGroupsCount
        get() = if (complementaryItems.any { it.isRequiredBySelection }) 1
        else 0
    private val optionalGroupsCount
        get() = if (complementaryItems.any { !it.isRequiredBySelection }) 1
        else 0
    private val layoutInflater = LayoutInflater.from(context)
    private var qtdOfGroupsInflated = 0

    inner class ComplementaryItemVH(val customItemView: CustomItemView) :
        RecyclerView.ViewHolder(customItemView) {

        lateinit var item: ItemBetweenUiAndVM

        private lateinit var endView: LayoutButtonCustomElementBinding

        fun bindClick(click: (Boolean) -> Unit) {

            if (item.wasSelectedYet) viewSelectedHandle(customItemView, item)

            customItemView.onItemClick {
                viewSelectedHandle(customItemView, item)
                click(false)
            }
        }

        @SuppressLint("SetTextI18n")
        private fun viewSelectedHandle(itemView: CustomItemView, item: ItemBetweenUiAndVM) {

            when (itemView.currentItemState) {

                CustomItemView.VIEW_NOT_SELECTED_YET -> {

                    if (itemView.endBinding == null) {

                        endView = LayoutButtonCustomElementBinding.inflate(layoutInflater)

                        endView.textAmountItem.text =
                            item.amountAdded.coerceAtLeast(item.minForSelection).toString()
                        endView.buttonDecrementItem.setOnClickListener {
                            val changeAmountInView =
                                itemView.endBinding as LayoutButtonCustomElementBinding

                            if (!checkAndChangeItemAmount(
                                    changeAmountInView.textAmountItem, -1, item.minForSelection
                                )
                            ) {

                                itemView.removeView()
                                item.wasSelectedYet = false
                            }

                            item.amountAdded = endView.textAmountItem.text.toString().toInt()
                        }
                        endView.buttonIncrementItem.setOnClickListener {
                            val changeAmountInView =
                                itemView.endBinding as LayoutButtonCustomElementBinding

                            checkAndChangeItemAmount(
                                changeAmountInView.textAmountItem, 1, item.maxForSelection
                            )
                            item.amountAdded = endView.textAmountItem.text.toString().toInt()
                        }

                    }
                    itemView.addEndView(endView)
                    item.wasSelectedYet = true
                }

                CustomItemView.VIEW_ALREADY_SELECTED -> {

                    checkAndChangeItemAmount(
                        (itemView.endBinding as LayoutButtonCustomElementBinding).textAmountItem,
                        1,
                        item.maxForSelection
                    )
                    item.amountAdded = endView.textAmountItem.text.toString().toInt()
                }
            }
        }

        @SuppressLint("SetTextI18n")
        private fun checkAndChangeItemAmount(
            textView: TextView, valueToChange: Int, maxLimit: Int
        ): Boolean {
            val canIncrement = textView.text.toString().toInt() < maxLimit
            val canDecrement = textView.text.toString().toInt() > maxLimit

            if (canIncrement) {

                textView.text = (item.amountAdded + valueToChange).toString()
                return true

            } else if (canDecrement) {

                textView.text = (item.amountAdded + valueToChange).toString()
                return true
            }

            return false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplementaryItemVH {

        return ComplementaryItemVH(CustomItemView(viewType, parent.context))
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onBindViewHolder(holder: ComplementaryItemVH, position: Int) {

        if (holder.customItemView.viewType == UNSPECIFIED) {
            val isRequiredGroup = (position == requiredGroupsCount - 1)

            if (isRequiredGroup) holder.customItemView.setItemName("Required Items")
            else holder.customItemView.setItemName("Optional Items")

            qtdOfGroupsInflated++

        } else if (holder.customItemView.viewType == ITEM_TYPE) {

            holder.item = complementaryItems[position - qtdOfGroupsInflated]
            holder.customItemView.setImage(holder.item.image)
            holder.customItemView.setItemName(holder.item.name)
            holder.bindClick(bindClick)
        }
    }

    override fun getItemCount(): Int =
        (requiredGroupsCount + optionalGroupsCount) + complementaryItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateRequiredSides(newRequiredSides: List<ItemBetweenUiAndVM>) {

        complementaryItems = newRequiredSides
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == requiredGroupsCount - 1 || position == complementaryItems.size) UNSPECIFIED else ITEM_TYPE
    }
}
