package com.devmobile.android.restaurant.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.devmobile.android.restaurant.viewholders.FragmentTabFoodSection

class FragmentTabAdapter(

    fragment: FragmentActivity,
    private val fragments: Array<FragmentTabFoodSection>,

    ) : FragmentStateAdapter(fragment) {

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {


        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {

        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {

        return fragments[position]
    }
}


