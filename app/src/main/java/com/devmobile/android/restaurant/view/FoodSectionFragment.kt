package com.devmobile.android.restaurant.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmobile.android.restaurant.databinding.LayoutRecyclerviewFoodsBinding
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.view.adapters.FoodAdapter
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.FoodChoiceViewModel
import kotlinx.coroutines.launch

class FoodSectionFragment : Fragment() {

    private val _binding: LayoutRecyclerviewFoodsBinding by lazy {
        LayoutRecyclerviewFoodsBinding.inflate(layoutInflater)
    }
    private val parentViewModel: FoodChoiceViewModel by activityViewModels()
    private var _foods: ArrayList<Food> = arrayListOf()

    private val restaurantId: Long by lazy {
        requireArguments().getStringArray("ARGUMENTS")!![0].toLong()
    }
    private val sectionName: String by lazy {
        requireArguments().getStringArray("ARGUMENTS")!![1]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init()
        return _binding.root
    }

    private fun init() {

        _binding.recyclerFood.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {

            fetchFoods()
            setUpFoodList()
        }
    }

    private suspend fun fetchFoods() {

        _foods = parentViewModel.fetchFoods(restaurantId, sectionName) as ArrayList
    }

    private fun setUpFoodList() {

        _binding.recyclerFood.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

        if (_foods.isNotEmpty()) {

            _binding.recyclerFood.adapter = FoodAdapter(_foods) { mustAdd, foodId ->

                if (mustAdd) {

                    parentViewModel.onAddFood(restaurantId, foodId)
                } else {
                    parentViewModel.onRemoveFood(foodId)
                }
            }
        }
    }
}