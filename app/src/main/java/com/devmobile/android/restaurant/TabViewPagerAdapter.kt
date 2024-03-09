package com.devmobile.android.restaurant

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class TabViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    val tabs = arrayOf(
        R.string.tab_item_entradas,
        R.string.tab_item_pratos_principais,
        R.string.tab_item_bebidas,
        R.string.tab_item_sobremesas,
        R.string.tab_todos_itens
    )

    private val fragments = arrayOf(
        FragmentRestaurant(),
        FoodSection(),
        FoodSection(),
        FoodSection(),
        FoodSection()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}