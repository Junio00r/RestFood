package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.devmobile.android.restaurant.databinding.ActivityFoodChoiceBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.FoodChoiceRepository
import com.devmobile.android.restaurant.model.repository.datasource.remote.DatabaseSimulator
import com.devmobile.android.restaurant.view.FoodSectionFragment
import com.devmobile.android.restaurant.view.adapters.TabAdapter
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.FoodChoiceViewModel
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

            }

            _viewModel.foodRemove.observe(this@FoodChoiceActivity) { foodId ->

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

    private fun setUpTab() {

        lifecycleScope.launch {

            val tabsName = _viewModel.fetchSections(_restaurantId)
            val tabs = createTabs(tabsName)

            tabs.let { _binding.viewPager2.adapter = TabAdapter(this@FoodChoiceActivity, it) }

            TabLayoutMediator(_binding.tabLayout, _binding.viewPager2) { tab, position ->

                tab.text = tabsName[position]
            }.attach()
        }
    }

    private fun createTabs(tabsName: List<String>): List<Fragment> {

        return tabsName.map { FoodSectionFragment(_restaurantId, it) }
    }

    private fun setUpSearch() {

        _binding.searchViewFoods.setupWithSearchBar(_binding.searchBarFoods)
    }

    private fun createFakeRemoteDatabase() {

        lifecycleScope.launch(Dispatchers.Default) {

            DatabaseSimulator.addRestaurants(this@FoodChoiceActivity)
            DatabaseSimulator.addFoodDataToDatabase(this@FoodChoiceActivity)
        }
    }
}


