package com.devmobile.android.restaurant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    fun register(user: User?): Boolean {

        viewModelScope.launch {


        }

        return false
    }
}