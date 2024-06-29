package com.devmobile.android.restaurant.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.model.repository.remotedata.FormRepository
import com.devmobile.android.restaurant.model.repository.remotedata.LoginRepository
import com.devmobile.android.restaurant.model.repository.remotedata.VerificationRepository

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

            modelClass.isAssignableFrom(FormViewModel::class.java) -> {
                if (repository as? FormRepository != null) {

                    @Suppress("UNCHECKED_CAST")
                    FormViewModel(repository, handle) as T

                } else {

                    throw ClassCastException("Não é possível fabricar uma intancia de Register Repository")
                }
            }

            modelClass.isAssignableFrom(VerificationViewModel::class.java) -> {
                if (repository as? VerificationRepository != null) {

                    @Suppress("UNCHECKED_CAST")
                    VerificationViewModel(repository, handle) as T

                } else {

                    throw ClassCastException("Não é possível fabricar uma intancia de Register Repository")
                }
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}