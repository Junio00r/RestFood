package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import android.util.Log
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.model.datasource.local.entities.getUiLayerItem
import com.devmobile.android.restaurant.model.repository.BagRemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class BagSharedViewModel(
    val restaurantId: Long? = null,
    val itemId: Long? = null,
    private val repository: BagRemoteRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : ViewModel() {


//    init {
//
//        if (restaurantId != null && itemId != null) {
//
//            coroutineScope.launch {
//                val newItem = repository.requestItem(restaurantId, itemId)
//                newItem.also {
//                    _itemName.value = it.name
//                    _itemDescription.value = it.description
//                    it.complementarySides?.map { complementaryItem ->
//                        repository.requestItem(restaurantId, complementaryItem)
//                    }?.also { complementaryItems ->
//                        _requiredSides.value = complementaryItems.map {
//                            ItemBetweenUiAndVM(
//                                it.name,
//                                0,
//                                it.description.toString(),
//                                it.imageId,
//                                it.price,
//                                it.minItemsBySelection,
//                                it.maxItemsBySelection,
//                                false,
//                                it.isRequiredBySelection ?: false,
//                                it.timeToPrepareInMin,
//                            )
//                        }
//                    }
//                }.also {
//                    _currentItem.value = it
//                }
//            }
//        }
//    }

    private val _currentItem = MutableSharedFlow<BagItem>(replay = 0)
    val currentItem = _currentItem.asSharedFlow()

    private val _itemName = MutableStateFlow<String?>(null)
    val itemName = _itemName.asStateFlow()

    private val _itemDescription = MutableStateFlow<String?>(null)
    val itemDescription = _itemDescription.asStateFlow()

    private val _requiredSides = MutableStateFlow<List<ItemBetweenUiAndVM>>(emptyList())
    val requiredSides = _requiredSides.asStateFlow()

    private val _amountItemAdded = MutableStateFlow(1)
    val amountItemAdded = _amountItemAdded.asStateFlow()


    private val _itemsOnBag = MutableStateFlow<List<BagItem>>(emptyList())
    val itemsOnBag = _itemsOnBag.asStateFlow()

    init {

        coroutineScope.launch {

            currentItem = repository.requestItem(restaurantId, itemId)
            currentItem.let {
                _itemName.value = it.name
                _itemDescription.value = it.description
                it.complementarySides?.map { complementaryItem ->
                    repository.requestItem(restaurantId, complementaryItem)
                }?.also { complementaryItems ->
                    _requiredSides.value = complementaryItems.map {
                        ItemBetweenUiAndVM(
                            it.name,
                            0,
                            it.description.toString(),
                            it.imageId,
                            it.price,
                            it.minItemsBySelection,
                            it.maxItemsBySelection,
                            false,
                            it.isRequiredBySelection ?: false,
                            it.timeToPrepareInMin,
                        )
                    }
                }
            }
        }
    }

    fun updateItemObservation(newText: String) {

        if (newText != _itemObservation.value)
            _itemObservation.value = newText
    }

    fun incrementTrigger() {

        if (amountItemAdded.value <= 10000)
            _amountItemAdded.value += 1
    }

    fun decrementTrigger() {

        if (amountItemAdded.value >= 2)
            _amountItemAdded.value -= 1
    }

    fun addItemOnBag(bagItem: BagItem) {
        viewModelScope.launch {

            _itemsOnBag.emit(listOf(bagItem))
        }
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }

    companion object {

        fun provideFactory(
            itemId: Long,
            restaurantId: Long,
            repository: ItemSelectedRemoteRepository
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory() {

                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {

                    return ItemSelectedViewModel(itemId, restaurantId, repository, handle) as T
                }
            }
    }
}

data class BagItem(val item: Item, val complementaryItems: List<ItemBetweenUiAndVM>)

data class ItemBetweenUiAndVM(
    val name: String,
    var amountAdded: Int,
    val description: String,
    val image: Int,
    val price: Float,
    val minForSelection: Int,
    val maxForSelection: Int,
    var wasSelectedYet: Boolean = false,
    val isRequiredBySelection: Boolean,
    val timeToPrepareInMin: Int
)