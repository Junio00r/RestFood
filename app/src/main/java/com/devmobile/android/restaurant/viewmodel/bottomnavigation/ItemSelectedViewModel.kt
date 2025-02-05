package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.model.repository.ItemSelectedRemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemSelectedViewModel(
    private val itemId: Long,
    private val restaurantId: Long,
    private val repository: ItemSelectedRemoteRepository,
    private val savedStateHandle: SavedStateHandle,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) : ViewModel() {

    lateinit var currentItem: Item
        private set

    private val _itemObservation = MutableStateFlow<String?>(null)
    val itemObservation = _itemObservation.asStateFlow()

    private val _itemName = MutableStateFlow<String?>(null)
    val itemName = _itemName.asStateFlow()

    private val _itemDescription = MutableStateFlow<String?>(null)
    val itemDescription = _itemDescription.asStateFlow()

    private val _requiredSides = MutableStateFlow<List<ItemBetweenUiAndVM>>(emptyList())
    val requiredSides = _requiredSides.asStateFlow()

    private val _amountItemAdded = MutableStateFlow(1)
    val amountItemAdded = _amountItemAdded.asStateFlow()

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