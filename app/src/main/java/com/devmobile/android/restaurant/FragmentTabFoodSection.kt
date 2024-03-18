package com.devmobile.android.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.adapters.FoodCardAdapter
import com.devmobile.android.restaurant.databinding.TabFoodSectionLayoutBinding
import java.util.LinkedList

class FragmentTabFoodSection : Fragment(R.layout.tab_food_section_layout) {
    private lateinit var binding: TabFoodSectionLayoutBinding
    private val iconSize = 64f
    private val filtersChip = LinkedList<CustomChipFilter>()
    private lateinit var recyclerViewFoods: RecyclerView
    private lateinit var customAdapter: FoodCardAdapter
    var dataFoodsOfTabSections = ArrayList<Food>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(context, "Chegou onCreateView", Toast.LENGTH_SHORT).show()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = TabFoodSectionLayoutBinding.bind(view)
        recyclerViewFoods = binding.recyclerFood
        customAdapter = FoodCardAdapter(dataFoodsOfTabSections, requireContext())
        recyclerViewFoods.adapter = customAdapter
        recyclerViewFoods.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        loadChipsOnFragment()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Toast.makeText(context, "Chegou onViewStateRestored", Toast.LENGTH_SHORT).show()
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Toast.makeText(context, "Chegou onStart", Toast.LENGTH_SHORT).show()
        super.onStart()
    }

    override fun onResume() {
        Toast.makeText(context, "Chegou onResume", Toast.LENGTH_SHORT).show()
        super.onResume()
    }

    override fun onPause() {
        Toast.makeText(context, "Chegou onPause", Toast.LENGTH_SHORT).show()
        super.onPause()
    }

    override fun onStop() {
        Toast.makeText(context, "Chegou onStop", Toast.LENGTH_SHORT).show()
        super.onStop()
    }

    override fun onDestroy() {
        Toast.makeText(context, "Chegou onDestroy", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Toast.makeText(context, "Chegou onSaveInstanceState", Toast.LENGTH_SHORT).show()
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Toast.makeText(context, "Chegou onDestroyView", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
    }

    private fun loadChipsOnFragment() {
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