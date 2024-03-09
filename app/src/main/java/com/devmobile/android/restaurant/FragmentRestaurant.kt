package com.devmobile.android.restaurant

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.devmobile.android.restaurant.databinding.FragmentRestaurantBinding

class FragmentRestaurant : Fragment(R.layout.fragment_restaurant) {
    private lateinit var binding: FragmentRestaurantBinding
    private val iconSize = 10f
    private val filtersChip = ArrayList<CustomChipFilter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let { createChips() }

        binding = FragmentRestaurantBinding.bind(view)
        binding.let {
            filtersChip.forEach {filter ->
                it.chipGroupFilters.addView(filter)
            }
        }
    }

    private fun createChips() {

        filtersChip.addAll(
            arrayOf(
                CustomChipFilter(requireContext(), "Ordenar"        , 0f, null, R.drawable.ic_chip_filter),
                CustomChipFilter(requireContext(), "Mais Recente"   , 0f, null, null),
                CustomChipFilter(requireContext(), "Preparo Rápido" , 0f, null, null),
                CustomChipFilter(requireContext(), "Preparo Lento"  , 0f, null, null),
                CustomChipFilter(requireContext(), "Filtros"        , 0f, null, R.drawable.ic_chip_filter_list)
            )
        )
    }
}