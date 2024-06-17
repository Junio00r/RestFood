package com.devmobile.android.restaurant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devmobile.android.restaurant.model.repository.remotedata.LoginRepository
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository

class ViewModelFactory(private val repository: Any) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

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
                    RegisterViewModel(repository) as T

                } else {

                    throw ClassCastException("Não é possível fabricar uma intancia de Register Repository")
                }
            }

            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}