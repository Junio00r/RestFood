package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.model.repository.bottomnavigation.HomeRepository
import com.devmobile.android.restaurant.model.repository.datasource.local.IRestaurantDao.RestaurantTuple
import com.devmobile.android.restaurant.usecase.InputPatterns
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    savedStateHandle: SavedStateHandle, private val homeRepository: HomeRepository
) : ViewModel() {

    companion object {

        fun provideFactory(
            repository: HomeRepository, owner: SavedStateRegistryOwner, defaultArgs: Bundle?
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

                override fun <T : ViewModel> create(
                    key: String, modelClass: Class<T>, handle: SavedStateHandle
                ): T {

                    @Suppress("UNCHECKED_CAST") return HomeViewModel(handle, repository) as T
                }
            }
    }

    private val _errorPropagator = MutableSharedFlow<Exception>()
    val errorPropagator = _errorPropagator.asSharedFlow()

    private val _cachedFetch = MutableSharedFlow<List<String>>()
    val cachedFetches = _cachedFetch.asSharedFlow()

    private val _restaurantsFetched = MutableSharedFlow<List<RestaurantTuple>>()
    val restaurantsFetched = _restaurantsFetched.asSharedFlow()

    fun searchQueryMatches(query: String) {

        viewModelScope.launch {

            if (query.isEmpty()) {

                _cachedFetch.emit(homeRepository.fetchCacheFetched())

            } else if (isValidQuery(query)) {

                _restaurantsFetched.emit(homeRepository.fetchRestaurants(query))

            } else {

                _errorPropagator.emit(Exception("Invalid Search"))
            }
        }
    }

    fun removeCache(query: String) {

        viewModelScope.launch {

            homeRepository.removeFromCache(query)
            _cachedFetch.emit(homeRepository.fetchCacheFetched())
        }
    }

    fun saveInCache(query: String) {

        viewModelScope.launch {

            homeRepository.saveFetchInCache(query)
        }
    }

    private fun isValidQuery(query: String?): Boolean {

        return InputPatterns.isMatch(InputPatterns.TEXT_PATTERN, query).isMatch
    }
}
