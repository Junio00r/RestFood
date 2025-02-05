package com.devmobile.android.restaurant.viewmodel.bottomnavigation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BagViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _itemsOnBag = MutableStateFlow<List<BagItem>>(emptyList())
    val itemsOnBag = _itemsOnBag.asStateFlow()

    fun addItemOnBag(bagItem: BagItem) {
        viewModelScope.launch {

            _itemsOnBag.emit(listOf(bagItem))
        }
    }
}

data class BagItem(val item: Item, val complementaryItems: List<ItemBetweenUiAndVM>)