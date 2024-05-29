package com.devmobile.android.restaurant.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.InputPatterns
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    private val _userNameError = MutableLiveData<String?>()
    val userNameError = _userNameError

    private val _userLastNameError = MutableLiveData<String?>()
    val userLastNameError = _userLastNameError

    private val _userEmailError = MutableLiveData<String?>()
    val userEmailError = _userEmailError

    private val _userPasswordError = MutableLiveData<String?>()
    val userPasswordError = _userPasswordError

    companion object {
        const val VALID_DATA = "VALID"
    }

    fun register(
        userName: String?, userLastName: String? = "", userEmail: String?, userPassword: String?
    ): Boolean {

        if (!isValidData(userName, userEmail, userPassword)) {

            return false
        }

        val newUser = User(
            null,
            userName!!.trim(),
            userLastName!!.trim(),
            userEmail!!.trim(),
            userPassword!!.trim(),
        )

        viewModelScope.launch {

            registerRepository.register(newUser)
        }
        // Lancar a tela de loading

        return true
    }

    private fun isValidData(
        userName: String?, userEmail: String?, userPassword: String?
    ): Boolean {

        if (!InputPatterns.matcher(InputPatterns.USERNAME_PATTERN, userName)) {

            _userNameError.value = "Invalid Name"
        } else {
            _userNameError.value = VALID_DATA
        }

        if (!InputPatterns.matcher(InputPatterns.USERNAME_PATTERN, userEmail)) {

            _userEmailError.value = "Invalid Email"
        } else {
            _userEmailError.value = VALID_DATA
        }

        if (!InputPatterns.matcher(InputPatterns.USERNAME_PATTERN, userPassword)) {

            _userPasswordError.value = "Passwords Don't Correspondent"

            return false
        } else {
            _userPasswordError.value = VALID_DATA
        }

        return true
    }
}