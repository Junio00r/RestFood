package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.devmobile.android.restaurant.databinding.ActivityMenuBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.MenuRepository
import com.devmobile.android.restaurant.model.repository.datasource.local.DatabaseSimulator
import com.devmobile.android.restaurant.view.TabSectionFragment
import com.devmobile.android.restaurant.view.adapters.TabAdapter
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.MenuViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MenuActivity : AppCompatActivity() {

    private val _binding: ActivityMenuBinding by lazy {
        ActivityMenuBinding.inflate(layoutInflater)
    }
    private val _restaurantId: Long by lazy {
        intent.getLongExtra("RESTAURANT_ID", 0)
    }
    private val _repository: MenuRepository by lazy {
        MenuRepository(
            restaurantDao = RestaurantLocalDatabase.getInstance(this).getRestaurantDao(),
            foodDao = RestaurantLocalDatabase.getInstance(this).getFoodDao()
        )
    }
    private val _viewModel: MenuViewModel by viewModels {
        MenuViewModel.provideFactory(_repository, owner = this@MenuActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createFakeRemoteDatabase()

        setContentView(_binding.root)

        setUpSearch()
        setUpTab()
    }

    private fun setUpTab() {

        lifecycleScope.launch {

            val tabsName = _viewModel.requestSections(_restaurantId)
            val tabs = createTabs(tabsName)

            tabs.let { _binding.viewPager2.adapter = TabAdapter(this@MenuActivity, it) }

            TabLayoutMediator(_binding.tabLayout, _binding.viewPager2) { tab, position ->

                tab.text = tabsName[position]
            }.attach()
        }
    }

    private fun createTabs(tabsName: List<String>): List<Fragment> {

        return tabsName.map { TabSectionFragment(_restaurantId, it) }
    }

    private fun setUpSearch() {
        _binding.searchViewFoods.setupWithSearchBar(_binding.searchBarFoods)
    }

    private fun createFakeRemoteDatabase() {

        lifecycleScope.launch(Dispatchers.Default) {

            DatabaseSimulator.addRestaurants(this@MenuActivity)
            DatabaseSimulator.addFoodDataToDatabase(this@MenuActivity)
        }
    }
}


