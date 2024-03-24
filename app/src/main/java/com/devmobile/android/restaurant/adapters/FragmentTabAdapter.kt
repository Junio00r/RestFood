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
import kotlinx.coroutines.newFixedThreadPoolContext

class FragmentTabAdapter(

    fragment: FragmentActivity,
    context: Context,
    val tabsNameId: Array<Int>,
    private val fragments: Array<FragmentTabFoodSection>,

    ) : FragmentStateAdapter(fragment) {

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {

        return fragments[position]
    }
}


