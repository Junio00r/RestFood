package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.model.repository.FoodChoiceRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FoodChoiceViewModel(
    private val foodChoiceRepository: FoodChoiceRepository,
    private val saveHandle: SavedStateHandle,
) : ViewModel() {

    companion object {

        fun provideFactory(
            repository: FoodChoiceRepository,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
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

    val tabPosition: Int
        get() = saveHandle["TAB_POSITION"] ?: 0

    fun updateSection(newSection: Int?) {
        saveHandle["TAB_POSITION"] = newSection
    }

    private val _foodRemove = MutableLiveData<Long>()
    val foodRemove: LiveData<Long> = _foodRemove

    private val _foodAdd = MutableLiveData<Food>()
    val foodAdd: LiveData<Food> = _foodAdd

    private val _requiredSides = MutableLiveData<List<Food>>()
    val requiredSides: LiveData<List<Food>> = _requiredSides

    fun onAddFood(restaurantId: Long, foodId: Long) {

        viewModelScope.launch {

            val foodSelected = foodChoiceRepository.requestFood(restaurantId, listOf(foodId)).first()
            _foodAdd.value = foodSelected

            val requiredSides = foodSelected.requiredSides?.let { foodChoiceRepository.requestFood(restaurantId, it) }
            requiredSides?.let { listOfFoods -> _requiredSides.value = listOfFoods }

        }
    }

    fun onRemoveFood(foodId: Long) {

        viewModelScope.launch {

            _foodRemove.value = foodId
        }
    }

    suspend fun fetchFoodsByPattern(restaurantId: Long, pattern: String): List<Food> {

        return viewModelScope.async {
            foodChoiceRepository.requestFoodsByPattern(pattern)
        }.await()
    }

    suspend fun fetchSections(restaurantId: Long): List<String> {

        return viewModelScope.async {

            foodChoiceRepository.requestSections(restaurantId).toList()
        }.await()
    }

    suspend fun fetchFoods(restaurantId: Long, section: String? = null): List<Food> {

        return viewModelScope.async {

            foodChoiceRepository.requestFoodsBySections(restaurantId, section)
        }.await()
    }
}
