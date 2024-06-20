package com.devmobile.android.restaurant.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.model.repository.remotedata.LoginRepository
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository

class ViewModelFactory(
    private val repository: Any,
    ownerOfStateToSave: SavedStateRegistryOwner,
    defaultValuesForNulls: Bundle? = null
) : AbstractSavedStateViewModelFactory(ownerOfStateToSave, defaultValuesForNulls) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return when {

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                if (repository as? LoginRepository != null) {

                    @Suppress("UNCHECKED_CAST")
                    LoginViewModel(repository) as T

                } else {

                    throw ClassCastException("Não é possível fabricar uma intancia de Login Repository")
                }
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                if (repository as? RegisterRepository != null) {

                    @Suppress("UNCHECKED_CAST")
                    RegisterViewModel(repository, handle) as T

                } else {

                    throw ClassCastException("Não é possível fabricar uma intancia de Register Repository")
                }
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}