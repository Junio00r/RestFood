package com.devmobile.android.restaurant.viewmodel.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.devmobile.android.restaurant.view.viewholders.FragmentTabFoodSection

class TabAdapter(

    fragment: FragmentActivity,
    private val fragmentsOfTab: Array<FragmentTabFoodSection>,

    ) : FragmentStateAdapter(fragment) {


    override fun onBindViewHolder(
        holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>
    ) {

        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {

        return fragmentsOfTab.size
    }

    override fun createFragment(position: Int): Fragment {

        return fragmentsOfTab[position]
    }

//    fun getQuantityOfFoodsSelected(): Int {
//
//        var totalOfFoodsSelected = 0
//
//        fragmentsOfTab.forEach {
//            totalOfFoodsSelected += it.getQuantityOfFoodsSelected()
//        }
//
//        return totalOfFoodsSelected
//    }
}


