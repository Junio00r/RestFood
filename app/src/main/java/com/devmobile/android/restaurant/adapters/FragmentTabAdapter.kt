package com.devmobile.android.restaurant.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devmobile.android.restaurant.viewholders.FragmentTabFoodSection

class FragmentTabAdapter(

    fragment: FragmentActivity,
    context: Context,
    val tabsNameId: Array<Int>,
    private val fragments: Array<FragmentTabFoodSection>,

    ) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {

        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {

        return fragments[position]
    }
}



