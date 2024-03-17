package com.devmobile.android.restaurant

import android.content.Context
import androidx.compose.ui.util.fastFilter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import java.util.LinkedList

class FragmentTabAdapter(

    private val fragment: FragmentActivity,
    private val context: Context,
    val tabsNameId: Array<Int>,
    private val fragments: Array<FragmentTabFoodSection>,
    private val allFoods: ArrayList<Food>

) : FragmentStateAdapter(fragment) {
    private val listFoodOfSections = FoodSection.entries.toTypedArray()

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {

        if (fragments[position].dataFoodsOfTabSections.size == 0) {

            fragments[position].dataFoodsOfTabSections = getFoodsOfTabSectionSpecific(position)
        }

        return fragments[position]
    }

    private fun getFoodsOfTabSectionSpecific(position: Int) : ArrayList<Food> {

        return allFoods.fastFilter { it.foodSection == listFoodOfSections[position] } as ArrayList<Food>
    }
}


