package com.devmobile.android.restaurant

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.devmobile.android.restaurant.databinding.FragmentRestaurantBinding

class FragmentRestaurant : Fragment(R.layout.fragment_restaurant) {
    private lateinit var binding: FragmentRestaurantBinding
    private val iconSize = 64f
    private val filtersChip = ArrayList<CustomChipFilter>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {

            context?.let { createChips() }
            binding = FragmentRestaurantBinding.bind(view)

            binding.let {
                filtersChip.forEach { filter ->

                    if (it.chipGroupFilter.childCount <= filtersChip.size) {
                        it.chipGroupFilter.addView(filter)
                    } else {
                        it.chipGroupFilter.removeView(filter)
                    }
                }
            }

        } else {

            binding = FragmentRestaurantBinding.bind(view)
            binding.let {
                filtersChip.forEach { filter ->

                    if (it.chipGroupFilter.parent == null) {
                        it.chipGroupFilter.addView(filter)
                    } else {
                        it.chipGroupFilter.removeView(filter)
                    }
                }
            }
        }
    }

    private fun createChips() {

        filtersChip.addAll(
            arrayOf(
                CustomChipFilter(requireContext(),"Ordenar",iconSize,null,R.drawable.ic_chip_filter),
                CustomChipFilter(requireContext(), "Mais Recente", iconSize, null, null),
                CustomChipFilter(requireContext(), "Preparo Rápido", iconSize, null, null),
                CustomChipFilter(requireContext(), "Preparo Lento", iconSize, null, null),
                CustomChipFilter(requireContext(),"Filtros",iconSize,null,R.drawable.ic_chip_filter_list)
            )
        )
    }
}