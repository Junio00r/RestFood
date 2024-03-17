package com.devmobile.android.restaurant

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.withInfiniteAnimationFrameNanos
import androidx.compose.ui.node.getOrAddAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.devmobile.android.restaurant.databinding.FragmentRestaurantBinding
import com.devmobile.android.restaurant.recyclerview.FoodCustomAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.LinkedList

class FragmentRestaurant : Fragment(R.layout.fragment_restaurant) {
    private lateinit var binding: FragmentRestaurantBinding
    private val iconSize = 64f
    private val filtersChip = LinkedList<CustomChipFilter>()
    private lateinit var recyclerViewFoods: RecyclerView
    private lateinit var customAdapter: FoodCustomAdapter
    var foods = LinkedList<Food>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRestaurantBinding.bind(view)
        recyclerViewFoods = binding.recyclerFood
        customAdapter = context?.let { FoodCustomAdapter(foods, it) }!!
        recyclerViewFoods.adapter = customAdapter
        recyclerViewFoods.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        if (filtersChip.size == 0) {

            createChips()
            binding.let {
                filtersChip.forEach { filter ->

                    if (filter.parent != null) {
                        (filter.parent as ViewGroup).removeView(filter)
                    }
                    it.chipGroupFilter.addView(filter)
                }
            }

        } else {

            binding.let {
                filtersChip.forEach { filter ->

                    if (filter.parent != null) {
                        (filter.parent as ViewGroup).removeView(filter)
                    }
                    it.chipGroupFilter.addView(filter)
                }
            }
        }
    }

    private fun createChips() {

        filtersChip.addAll(
            arrayOf(
                CustomChipFilter(
                    requireContext(),
                    "Ordenar",
                    iconSize,
                    null,
                    R.drawable.ic_chip_filter
                ),
                CustomChipFilter(
                    requireContext(),
                    "Mais Recente",
                    iconSize,
                    R.drawable.ic_check_chip_filter,
                    null
                ),
                CustomChipFilter(
                    requireContext(),
                    "Preparo Rápido",
                    iconSize,
                    R.drawable.ic_check_chip_filter,
                    null
                ),
                CustomChipFilter(
                    requireContext(),
                    "Preparo Lento",
                    iconSize,
                    R.drawable.ic_check_chip_filter,
                    null
                ),
            )
        )
    }
}