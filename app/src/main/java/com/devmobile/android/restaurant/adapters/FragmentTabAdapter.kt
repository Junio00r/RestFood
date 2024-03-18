package com.devmobile.android.restaurant.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.enums.FoodSection
import com.devmobile.android.restaurant.FragmentTabFoodSection
import com.devmobile.android.restaurant.RestaurantDatabase

class FragmentTabAdapter(

    fragment: FragmentActivity,
    context: Context,
    val tabsNameId: Array<Int>,
    private val fragments: Array<FragmentTabFoodSection>,

) : FragmentStateAdapter(fragment) {
    private val listFoodOfSections = FoodSection.entries.toTypedArray()
    private val foodDao = RestaurantDatabase.getInstance(context).getFoodDao()

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

            fragments[position].dataFoodsOfTabSections = foodDao.getFoodsBySection(listFoodOfSections[0]) as ArrayList<Food>
        }

        return fragments[position]
    }

//    private fun getFoodsOfTabSectionSpecific(position: Int) : ArrayList<Food> {
//
//        return allFoods.fastFilter { it.foodSection == listFoodOfSections[position] } as ArrayList<Food>
//    }
}


