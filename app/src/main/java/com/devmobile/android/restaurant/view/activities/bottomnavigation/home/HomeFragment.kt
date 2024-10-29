package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmobile.android.restaurant.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.FragmentRestaurantHomeBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.bottomnavigation.HomeRepository
import com.devmobile.android.restaurant.model.repository.datasource.local.DatabaseSimulator
import com.devmobile.android.restaurant.view.HistoricAdapter
import com.devmobile.android.restaurant.view.HistoricItem
import com.devmobile.android.restaurant.view.RestaurantAdapter
import com.devmobile.android.restaurant.view.RestaurantItemList
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.BottomNavigationViewModel
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), IShowError {

    companion object {
        const val FRAGMENT_TAG = "HOME"
    }

    private val _binding: FragmentRestaurantHomeBinding by lazy {
        FragmentRestaurantHomeBinding.inflate(this.layoutInflater)
    }
    private val _repository: HomeRepository by lazy {
        HomeRepository(
            fetchDao = RestaurantLocalDatabase.getInstance(requireContext()).getFetch(),
            restaurantDao = RestaurantLocalDatabase.getInstance(requireContext()).getRestaurantDao()
        )
    }
    private val _viewModel: HomeViewModel by viewModels {
        HomeViewModel.provideFactory(_repository, this, null)
    }
    private val _intentNext: Intent by lazy {
        Intent(requireContext(), FoodChoiceActivity::class.java)
    }

    private var fragmentIndex: Int? = null
    private val parentViewModel: BottomNavigationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            fragmentIndex = it.getInt("FRAGMENT_INDEX")
        }
        fragmentIndex?.let { parentViewModel.updateIndex(it) }
        setUpObservables()

        return _binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createFakeRemoteDatabase()
        _binding.searchViewHome.setupWithSearchBar(_binding.searchBarHome)
        _binding.recyclerHistoric.layoutManager = LinearLayoutManager(requireContext())
        _binding.recycleViewRestaurantsList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setUpObservables() {

        // When search button is clicked
        _binding.searchViewHome.editText.setOnEditorActionListener { _, _, _ ->

            _binding.searchViewHome.hide()
            return@setOnEditorActionListener false
        }
        _binding.searchViewHome.editText.doOnTextChanged { text, _, _, _ ->

            _viewModel.searchQueryMatches(text.toString())
        }

        _viewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->

            when (restaurants.isCacheable) {

                true -> {

                    populateFastHistoric(restaurants.restaurants.map {
                        HistoricItem(
                            R.drawable.image_sobremesa_brownie,
                            it
                        )
                    })

                    populateSearch(restaurants.restaurants.map {
                        RestaurantItemList(
                            startDrawable = R.drawable.ic_historic,
                            restaurantName = it,
                            endDrawable = R.drawable.ic_close_24,
                            endAction = RestaurantItemList.CLEAR_FROM_HISTORIC
                        )
                    })
                }

                false -> {

                    populateSearch(restaurants.restaurants.map {
                        RestaurantItemList(
                            restaurantName = it,
                            endDrawable = R.drawable.image_principal_feijoada
                        )
                    })
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                _viewModel.errorPropagator.collect { error ->
                    showErrorMessage(error.message ?: "Try Again")
                }
            }
        }
    }

    private fun populateFastHistoric(dataSet: List<HistoricItem>) {


        _binding.recyclerHistoric.adapter = HistoricAdapter(dataSet = dataSet) { restaurantName ->

            startActivity(_intentNext.putExtra("RESTAURANT_NAME", restaurantName))
        }
    }

    private fun populateSearch(dataSet: List<RestaurantItemList>) {

        _binding.recycleViewRestaurantsList.adapter =
            RestaurantAdapter(dataSet = dataSet, onItemClick = { clickAction, restaurantName ->

                when (clickAction) {

                    RestaurantItemList.CLEAR_FROM_HISTORIC -> {

                        _viewModel.removeCache(restaurantName)
                    }

                    RestaurantItemList.CLICK -> {

                        _viewModel.onSelect(restaurantName)
                        startActivity(_intentNext.putExtra("RESTAURANT_NAME", restaurantName))
                    }

                    else -> {

                        _viewModel.onSelect(restaurantName)
                        startActivity(_intentNext.putExtra("RESTAURANT_NAME", restaurantName))
                    }
                }
            })
    }

    private fun createFakeRemoteDatabase() {

        lifecycleScope.launch(Dispatchers.IO) {

            DatabaseSimulator.addRestaurants(requireContext())
        }
    }

    override fun showErrorMessage(errorMessage: String) {

        _binding.searchViewHome.editText.error = errorMessage
    }

    override fun onStop() {

        _binding.searchViewHome.hide()
        super.onStop()
    }
}