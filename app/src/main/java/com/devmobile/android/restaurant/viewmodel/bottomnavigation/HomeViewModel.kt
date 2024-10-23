package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.model.repository.bottomnavigation.HomeRepository
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

    private val _restaurants = MutableLiveData<Restaurants>()
    val restaurants: LiveData<Restaurants> = _restaurants.distinctUntilChanged()

    fun searchQueryMatches(query: String) {

        viewModelScope.launch {

            if (query.isEmpty()) {

                _restaurants.value = Restaurants(true, homeRepository.fetchCacheFetched())

            } else if (isValidQuery(query)) {

                _restaurants.value = Restaurants(false, homeRepository.fetchRestaurants(query))

            } else {

                _errorPropagator.emit(Exception("Invalid Search"))
            }
        }
    }

    fun removeCache(query: String) {

        viewModelScope.launch {

            homeRepository.removeFromCache(query)
            _restaurants.value = Restaurants(true, homeRepository.fetchCacheFetched())
        }
    }

    fun onSelect(query: String) {

        viewModelScope.launch {

            homeRepository.saveFetchInCache(query)
        }
    }

    private fun isValidQuery(query: String?): Boolean {

        return InputPatterns.isMatch(InputPatterns.TEXT_PATTERN, query).isMatch
    }

    data class Restaurants(val isCacheable: Boolean, val restaurants: List<String>)
}
