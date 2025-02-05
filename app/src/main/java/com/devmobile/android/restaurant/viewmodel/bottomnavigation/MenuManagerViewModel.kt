package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.model.repository.MenuManagerRemoteRepository
import com.devmobile.android.restaurant.usecase.RequestResult
import com.devmobile.android.restaurant.usecase.entities.Bag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MenuManagerViewModel(
    private val menuManagerRemoteRepository: MenuManagerRemoteRepository,
    private val saveHandle: SavedStateHandle,
    val restaurantId: Long,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : ViewModel() {

    var tabPosition: Int = 0

    private val _errorPropagator = MutableSharedFlow<RequestResult>()
    val errorPropagator = _errorPropagator.asSharedFlow()

    private val _restaurantItems = MutableLiveData<List<Item>>()
    val restaurantItems = _restaurantItems

    private val _onAddItemToBag = MutableLiveData<List<Item>>()
    val onAddItemToBag: LiveData<List<Item>> = _onAddItemToBag

    suspend fun fetchItemsByPattern(pattern: String): List<Item> {

        return coroutineScope.async {

            if(pattern.isNotEmpty())
                menuManagerRemoteRepository.requestItemsByPattern(restaurantId, pattern)
            else
                emptyList()

        }.await()
    }

    suspend fun fetchSections(): List<String> {

        return coroutineScope.async {

            menuManagerRemoteRepository.requestSections(restaurantId)
        }.await()
    }

    suspend fun fetchItems(section: String? = null): List<Item> {

        return coroutineScope.async {

            menuManagerRemoteRepository.requestItemsBySections(restaurantId, section)
        }.await()
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }

    companion object {

        fun provideFactory(
            repository: MenuManagerRemoteRepository,
            owner: SavedStateRegistryOwner,
            restaurantId: Long,
            defaultArgs: Bundle? = null
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

                override fun <T : ViewModel> create(
                    key: String, modelClass: Class<T>, handle: SavedStateHandle
                ): T {

                    @Suppress("UNCHECKED_CAST") return MenuManagerViewModel(
                        menuManagerRemoteRepository = repository,
                        saveHandle = handle,
                        restaurantId = restaurantId,
                    ) as T
                }
            }
    }
}

