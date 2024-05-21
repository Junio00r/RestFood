package com.devmobile.android.restaurant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devmobile.android.restaurant.model.repository.remotedata.LoginRepository

class LoginViewModelFactory(private val loginRepository: LoginRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}