package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.usecase.RequestResult
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.model.repository.MenuManagerRemoteRepository
import com.devmobile.android.restaurant.usecase.entities.Bag
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class MenuManagerViewModel(
    private val menuManagerRemoteRepository: MenuManagerRemoteRepository,
    private val saveHandle: SavedStateHandle,
    val restaurantId: Long,
    private val bag: Bag,
) : ViewModel() {

    var tabPosition: Int = 0

    private val _errorPropagator = MutableSharedFlow<RequestResult>()
    val errorPropagator = _errorPropagator.asSharedFlow()

    private val _restaurantItems = MutableLiveData<List<Item>>()
    val restaurantItems = _restaurantItems

    private val _onItemUnselected = MutableLiveData<Long>()
    val onItemUnselected: LiveData<Long> = _onItemUnselected

    private val _onItemSelected = MutableSharedFlow<Item?>()
    val onItemSelected = _onItemSelected.asSharedFlow()

    // Order details
    private val _requiredSides = MutableLiveData<List<Item>>()
    val requiredSides: LiveData<List<Item>> = _requiredSides

    private val _onAddItemToBag = MutableLiveData<List<Item>>()
    val onAddItemToBag: LiveData<List<Item>> = _onAddItemToBag

    fun onSelectItem(itemId: Long) {

        viewModelScope.launch {

            try {

                val itemSelected = menuManagerRemoteRepository.requestItems(restaurantId, listOf(itemId)).first()
                _onItemSelected.emit(null)

                val requiredSides = itemSelected.complementarySides?.let {
                    menuManagerRemoteRepository.requestItems(restaurantId, it.toLongArray().toList())
                }
                requiredSides?.let { listOfItems ->
                    _requiredSides.value = listOfItems
                }

            } catch (e: IndexOutOfBoundsException) {
                _errorPropagator.emit(RequestResult.Error(Exception("Item not Found")))
            }
        }
    }

    fun onUnselectedItem(itemId: Long) {

        viewModelScope.launch {

            _onItemUnselected.value = itemId
        }
    }

    suspend fun fetchItemsByPattern(pattern: String): List<Item> {

        return viewModelScope.async {
            menuManagerRemoteRepository.requestItemsByPattern(restaurantId, pattern)
        }.await()
    }

    suspend fun fetchItems(section: String? = null): List<Item> {

        return viewModelScope.async {

            menuManagerRemoteRepository.requestItemsBySections(restaurantId, section)
        }.await()
    }

    fun addItemToBag() {
        viewModelScope.launch {

            _onAddItemToBag.value = listOf(_onItemSelected.last()!!)
            bag.addItem(_onItemSelected.last()!!)
        }
    }

    suspend fun fetchSections(): List<String> {

        return viewModelScope.async {

            menuManagerRemoteRepository.requestSections(restaurantId).toList()
        }.await()
    }

    companion object {

        fun provideFactory(
            repository: MenuManagerRemoteRepository,
            owner: SavedStateRegistryOwner,
            restaurantId: Long,
            bag: Bag,
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
                        bag = bag
                    ) as T
                }
            }
    }
}

