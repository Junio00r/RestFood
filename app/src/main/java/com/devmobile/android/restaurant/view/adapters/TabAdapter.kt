package com.devmobile.android.restaurant.viewmodel.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(
    activity: FragmentActivity,
    private val tabsName: Array<out Fragment>,
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {

        return tabsName.size
    }

    override fun createFragment(position: Int): Fragment {

        return tabsName[position]
    }
}


