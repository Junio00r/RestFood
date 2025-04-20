package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.model.datasource.local.entities.ItemBetweenUiAndVM
import com.devmobile.android.restaurant.model.datasource.local.entities.getUiLayerItem
import com.devmobile.android.restaurant.model.repository.BagRemoteRepository
import com.devmobile.android.restaurant.usecase.RequestState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BagSharedViewModel(
    private val repository: BagRemoteRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)
) : ViewModel() {

    private val _errorPropagator = MutableSharedFlow<RequestState>()

    private val _wasItemAdded = MutableStateFlow<RequestState?>(null)
    val wasItemAdded = _wasItemAdded.asStateFlow()

    // Need create a object bag for improve the code...
    private val _itemsOnBag = MutableStateFlow<List<BagRestaurantGroup>>(emptyList())
    val itemsOnBag = _itemsOnBag.asStateFlow()

    // Item selected
    private var _currentItem: Item? = null

    private val _itemName = MutableStateFlow<String?>(null)
    val itemName = _itemName.asStateFlow()

    private val _itemDescription = MutableStateFlow<String?>(null)
    val itemDescription = _itemDescription.asStateFlow()

    private val _itemObservation = MutableStateFlow<String?>(null)
    val itemObservation = _itemObservation.asStateFlow()

    private val _newRequiredSides = MutableStateFlow<List<ItemBetweenUiAndVM>?>(null)
    val newRequiredSides = _newRequiredSides.asStateFlow()

    private val _amountItemAdded = MutableStateFlow(0)
    val amountItemAdded = _amountItemAdded.asStateFlow()

    fun updateItemObservation(newText: String) {

        if (newText != _itemObservation.value) _itemObservation.value = newText
    }

    fun incrementTrigger() {
        val canIncrement = amountItemAdded.value <= (_currentItem?.maxItemsAvailable ?: 1)

        if (canIncrement) _amountItemAdded.value += 1
    }

    fun decrementTrigger() {
        val canDecrement =
            amountItemAdded.value >= (_currentItem?.minItemsBySelection?.plus(1) ?: 1)

        if (canDecrement) _amountItemAdded.value -= 1
    }

    fun fetchItem(restaurantId: Long, itemId: Long) {

        if (_currentItem == null) {

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
                        _currentItem = item
                        _newRequiredSides.value = it
                    }
                }
            }
        }
    }

    fun addItemOnBag(restaurantId: Long? = null, itemId: Long? = null) {

        coroutineScope.launch {
            _wasItemAdded.value = RequestState.Loading

            _currentItem?.let { currentItem ->

                val itemToInsert = BagItem(currentItem, _newRequiredSides.value)
                val isThereThisRestaurantOnBag = _itemsOnBag.value.any { it.restaurantId == currentItem.restaurantId }

                if(isThereThisRestaurantOnBag) {
                    _itemsOnBag.value
                        .first { it.restaurantId == currentItem.restaurantId }
                        .addItem(itemToInsert)
                } else {

                    _itemsOnBag.value
                        .toMutableList()
                        .add(
                            BagRestaurantGroup(currentItem.restaurantId, mutableListOf(itemToInsert))
                        )
                }


                // Reset states
                _currentItem = null
                _newRequiredSides.value = null

                // Update request state
                _wasItemAdded.value = RequestState.Success()
                delay(200)
                _wasItemAdded.value = null
            }
        }
    }

    fun removeItemOnBag(restaurantId: Long, itemId: Long) {
        coroutineScope.launch {

            _itemsOnBag.value.first { it.restaurantId == restaurantId }.removeItem(itemId)
        }
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }

    companion object {

        fun provideFactory(
            repository: BagRemoteRepository
        ): AbstractSavedStateViewModelFactory = object : AbstractSavedStateViewModelFactory() {

            override fun <T : ViewModel> create(
                key: String, modelClass: Class<T>, handle: SavedStateHandle
            ): T {

                return BagSharedViewModel(repository) as T
            }
        }
    }
}

data class BagItem(val item: Item, val complementaryItems: List<ItemBetweenUiAndVM>? = null)

data class BagRestaurantGroup(
    val restaurantId: Long,
    val itemsAdded: MutableList<BagItem> = mutableListOf()
) {

    fun addItem(newItem: BagItem): Boolean {
        if (itemsAdded.contains(newItem)) {
            return false
        }

        itemsAdded.add(newItem)

        return true
    }

    fun removeItem(itemId: Long): Boolean {
        return itemsAdded.removeIf { it.item.id == itemId }
    }

    fun updateItem(itemId: Long, newItem: BagItem): Boolean {

        if (removeItem(itemId)) {
            return addItem(newItem)
        }
        return false
    }

    fun size(): Int {
        return itemsAdded.size
    }
}
