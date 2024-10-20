package com.devmobile.android.restaurant.view.activities.bottomnavigation

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
import com.devmobile.android.restaurant.databinding.FragmentRestaurantHomeBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.bottomnavigation.HomeRepository
import com.devmobile.android.restaurant.model.repository.datasource.local.DatabaseSimulator
import com.devmobile.android.restaurant.view.RestaurantAdapter
import com.devmobile.android.restaurant.view.RestaurantSearchItem
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.BottomNavigationViewModel
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), IShowError {

    companion object {
        const val FRAGMENT_TAG = "HOME"
    }

    private val _binding: FragmentRestaurantHomeBinding by lazy {
        FragmentRestaurantHomeBinding.inflate(this@HomeFragment.layoutInflater)
    }
    private val _repository: HomeRepository by lazy {
        HomeRepository(RestaurantLocalDatabase.getInstance(requireContext()))
    }
    private val _viewModel: HomeViewModel by viewModels {
        HomeViewModel.provideFactory(_repository, this, null)
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
        _binding.homeFragment = this

        setUpObservables()

        return _binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createFakeRemoteDatabase()
        _binding.searchViewHome.setupWithSearchBar(_binding.searchBarHome)
        _binding.recycleViewRestaurantsList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setUpObservables() {

        // When search button is click
        _binding.searchViewHome.editText.setOnEditorActionListener { v, actionId, event ->
            _binding.searchViewHome.hide()
            return@setOnEditorActionListener false
        }

        _binding.searchViewHome.editText.doOnTextChanged { text, _, _, _ ->
            _viewModel.searchQueryMatches(text.toString())
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                _viewModel.restaurantsFetched.collect { restaurants ->

                    populateRecycler(restaurants.map { restaurant ->
                        RestaurantSearchItem(name = restaurant.name, image = null)
                    }, false)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                _viewModel.cachedFetches.collect { cachedFetches ->

                    populateRecycler(cachedFetches.map { fetch ->
                        RestaurantSearchItem(name = fetch)
                    }, true)
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

    private fun populateRecycler(dataSet: List<RestaurantSearchItem>, isDataCached: Boolean) {

        _binding.recycleViewRestaurantsList.adapter = RestaurantAdapter(dataSet, isDataCached)
    }

    private fun createFakeRemoteDatabase() {

        lifecycleScope.launch(Dispatchers.IO) {

            DatabaseSimulator.addRestaurants(requireContext())
        }
    }

    override fun showErrorMessage(errorMessage: String) {
        _binding.searchViewHome.editText.error = errorMessage
    }
}