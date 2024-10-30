package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.model.repository.FoodChoiceRepository
import kotlinx.coroutines.async

class FoodChoiceViewModel(
    private val foodChoiceRepository: FoodChoiceRepository,
    private val saveHandle: SavedStateHandle,
) : ViewModel() {

    companion object {

        fun provideFactory(
            repository: FoodChoiceRepository, owner: SavedStateRegistryOwner, defaultArgs: Bundle? = null
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

                override fun <T : ViewModel> create(
                    key: String, modelClass: Class<T>, handle: SavedStateHandle
                ): T {

                    @Suppress("UNCHECKED_CAST") return FoodChoiceViewModel(
                        foodChoiceRepository = repository,
                        saveHandle = handle
                    ) as T
                }

            }
    }

    suspend fun fetchSections(restaurantId: Long): List<String> {

        return viewModelScope.async {

            foodChoiceRepository.requestSections(restaurantId).toList()
        }.await()
    }

    suspend fun fetchFoods(restaurantId: Long, section: String?): List<Food> {

        return viewModelScope.async {

            foodChoiceRepository.requestFoods(restaurantId, section)
        }.await()
    }
}
