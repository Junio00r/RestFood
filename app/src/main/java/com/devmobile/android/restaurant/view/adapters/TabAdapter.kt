package com.devmobile.android.restaurant.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(
    activity: FragmentActivity,
    private val tabs: List<Fragment>,
) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {

        return tabs[position]
    }

    override fun getItemCount(): Int {

        return tabs.size
    }
}


