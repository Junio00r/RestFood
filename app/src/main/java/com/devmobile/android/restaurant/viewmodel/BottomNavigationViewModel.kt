package com.devmobile.android.restaurant.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

class BottomNavigationViewModel(private val saveStateHandle: SavedStateHandle) : ViewModel() {

    companion object {

        fun provideFactory(
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle?
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {

                    @Suppress("UNCHECKED_CAST")
                    return BottomNavigationViewModel(saveStateHandle = handle) as T
                }

            }
    }

    val indexFragment: LiveData<Int>
        get() = saveStateHandle.getLiveData("POSITION")

    fun updateIndex(newIndex: Int) {
        saveStateHandle["POSITION"] = newIndex
    }
}
