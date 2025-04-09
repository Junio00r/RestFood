package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.model.datasource.local.entities.ItemBetweenUiAndVM
import com.devmobile.android.restaurant.model.datasource.local.entities.getUiLayerItem
import com.devmobile.android.restaurant.model.repository.BagRemoteRepository
import com.devmobile.android.restaurant.usecase.RequestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BagSharedViewModel(
    val restaurantId: Long? = null,
    val itemId: Long? = null,
    private val repository: BagRemoteRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : ViewModel() {

    private val _currentItem = MutableStateFlow<BagItem?>(null)
    val currentItem = _currentItem.asSharedFlow()

    private val _itemName = MutableStateFlow<String?>(null)
    val itemName = _itemName.asStateFlow()

    private val _itemDescription = MutableStateFlow<String?>(null)
    val itemDescription = _itemDescription.asStateFlow()

    private val _itemObservation = MutableStateFlow<String?>(null)
    val itemObservation = _itemObservation.asStateFlow()

    private val _newRequiredSides = MutableStateFlow<List<ItemBetweenUiAndVM>?>(null)
    val newRequiredSides = _newRequiredSides.asStateFlow()

    private val _amountItemAdded = MutableStateFlow(1)
    val amountItemAdded = _amountItemAdded.asStateFlow()

    private val _itemsOnBag = MutableStateFlow<List<BagItem>?>(null)
    val itemsOnBag = _itemsOnBag.asStateFlow()

    fun updateItemObservation(newText: String) {

        if (newText != _itemObservation.value) _itemObservation.value = newText
    }

    fun incrementTrigger() {
        val canIncrement =
            amountItemAdded.value <= (_currentItem.value?.item?.maxItemsAvailable ?: 1)

        if (canIncrement) _amountItemAdded.value += 1
    }

    fun decrementTrigger() {
        val canDecrement =
            amountItemAdded.value >= (_currentItem.value?.item?.minItemsBySelection?.plus(1) ?: 1)

        if (canDecrement) _amountItemAdded.value -= 1
    }

    fun fetchItem(restaurantId: Long, itemId: Long) {

        if (_currentItem.value == null) {

            coroutineScope.launch {
                repository.requestItem(restaurantId, itemId).also { item ->

                    val itemRequiredSides: List<ItemBetweenUiAndVM>? =
                        item.complementarySides?.map { complementaryItemId ->
                            repository.requestItem(restaurantId, complementaryItemId)
                                .getUiLayerItem()
                        }
                    _itemName.value = item.name
                    _itemDescription.value = item.description
                    _amountItemAdded.value = item.minItemsBySelection
                    itemRequiredSides?.let {
                        _currentItem.emit(BagItem(item, it))
                        _newRequiredSides.emit(it)
                    }
                }
            }
        }
    }

    fun addItemOnBag(itemId: Long) {
        viewModelScope.launch {

            _currentItem.value?.let {

                    _itemsOnBag.value = (_itemsOnBag.value ?: emptyList()) + it
            }
        }
    }

    fun removeItemOnBag(itemId: Long) {
        viewModelScope.launch {

//            _itemsOnBag.emit(listOf(bagItem))
        }
    }

    fun cancelItemSelection() {
        _currentItem.value = null
        _newRequiredSides.value = null
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }

    companion object {

        fun provideFactory(
            restaurantId: Long? = null,
            itemId: Long? = null,
            repository: BagRemoteRepository
        ): AbstractSavedStateViewModelFactory = object : AbstractSavedStateViewModelFactory() {

            override fun <T : ViewModel> create(
                key: String, modelClass: Class<T>, handle: SavedStateHandle
            ): T {

                return BagSharedViewModel(restaurantId, itemId, repository) as T
            }
        }
    }
}

data class BagItem(val item: Item, val complementaryItems: List<ItemBetweenUiAndVM>)