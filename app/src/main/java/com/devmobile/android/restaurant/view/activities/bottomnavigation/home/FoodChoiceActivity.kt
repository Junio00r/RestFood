package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmobile.android.restaurant.databinding.ActivityFoodChoiceBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.model.repository.FoodChoiceRepository
import com.devmobile.android.restaurant.model.repository.datasource.remote.DatabaseSimulator
import com.devmobile.android.restaurant.view.FoodSectionFragment
import com.devmobile.android.restaurant.view.adapters.FoodAdapter
import com.devmobile.android.restaurant.view.adapters.TabAdapter
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.FoodChoiceViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FoodChoiceActivity : AppCompatActivity() {

    private val _binding: ActivityFoodChoiceBinding by lazy {
        ActivityFoodChoiceBinding.inflate(layoutInflater)
    }
    private val _repository: FoodChoiceRepository by lazy {
        FoodChoiceRepository(
            restaurantDao = RestaurantLocalDatabase.getInstance(this).getRestaurantDao(),
            foodDao = RestaurantLocalDatabase.getInstance(this).getFoodDao()
        )
    }
    private val _viewModel: FoodChoiceViewModel by viewModels {
        FoodChoiceViewModel.provideFactory(_repository, owner = this@FoodChoiceActivity)
    }

    // data
    private val _restaurantId: Long by lazy {
        intent.getLongExtra("RESTAURANT_ID", 0)
    }
    private var foodAdapter: FoodAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createFakeRemoteDatabase()
        setContentView(_binding.root)

        setUpSearch()
        setUpTab()
        setObservables()
    }

    private fun setObservables() {

        lifecycleScope.launch {

            _viewModel.foodAdd.observe(this@FoodChoiceActivity) { food ->

                startActivity(Intent(this@FoodChoiceActivity, FoodDetailsOrderActivity::class.java))
            }

            _viewModel.foodRemove.observe(this@FoodChoiceActivity) { foodId ->
            }
        }

        _binding.searchViewFoods.editText.doOnTextChanged { text, _, _, _ ->

            lifecycleScope.launch {

                val newFoods = _viewModel.fetchFoodsByPattern(_restaurantId, text.toString())
                populateSearchFoods(newFoods)
            }
        }

        _binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

                tab?.let { _viewModel.updateSection(it.position) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun populateSearchFoods(foods: List<Food>) {

        _binding.recyclerSearchFood.adapter = FoodAdapter(foods) { mustAdd, foodId ->

            if (mustAdd) {
                startActivity(Intent(this@FoodChoiceActivity, FoodDetailsOrderActivity::class.java))
            }
        }
    }

    private fun setUpTab() {

        lifecycleScope.launch {

            val tabsSections = _viewModel.fetchSections(_restaurantId)
            val tabs = createTabs(tabsSections)

            _binding.viewPager2.offscreenPageLimit = 3
            _binding.viewPager2.adapter = TabAdapter(this@FoodChoiceActivity, tabs)
            _binding.viewPager2.setCurrentItem(_viewModel.tabPosition, false)

            TabLayoutMediator(_binding.tabLayout, _binding.viewPager2) { tab, position ->

                tab.text = tabsSections[position]
            }.attach()
        }
    }

    private fun createTabs(tabsSections: List<String>): List<Fragment> {

        return tabsSections.mapIndexed { _, sectionName ->

            FoodSectionFragment().apply {
                arguments = Bundle().apply {
                    putStringArray("ARGUMENTS", arrayOf(_restaurantId.toString(), sectionName))
                }
            }
        }
    }

    private fun setUpSearch() {

        _binding.searchViewFoods.setupWithSearchBar(_binding.searchBarFoods)
        _binding.recyclerSearchFood.layoutManager = LinearLayoutManager(this)
        _binding.recyclerSearchFood.adapter = FoodAdapter(emptyList())
    }

    private fun createFakeRemoteDatabase() {

        lifecycleScope.launch(Dispatchers.Default) {

            DatabaseSimulator.addRestaurants(this@FoodChoiceActivity)
            DatabaseSimulator.addFoodDataToDatabase(this@FoodChoiceActivity)
        }
    }
}


