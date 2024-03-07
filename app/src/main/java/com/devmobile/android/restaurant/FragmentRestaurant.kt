package com.devmobile.android.restaurant

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.devmobile.android.restaurant.databinding.FragmentRestaurantBinding

class FragmentRestaurant : Fragment(R.layout.fragment_restaurant) {

    private val chipSize = 24f
    private lateinit var binding: FragmentRestaurantBinding
    private val filterItems = arrayOf(
        FilterItem("Ordenar", chipSize, null, R.drawable.ios_arrow_down_1),
        FilterItem("Mais recente", chipSize, null, null),
        FilterItem("Preparo Rápido", chipSize, null, null),
        FilterItem("Preparo Lento", chipSize, null, null),
        FilterItem("Filtros", chipSize, null, R.drawable.filter_list_1)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRestaurantBinding.bind(view)

        binding.let {

            filterItems.forEach { filter ->
                it.chipGroupFilters.addView(
                    filter.toChip(requireContext())
                )
            }
        }
    }
}