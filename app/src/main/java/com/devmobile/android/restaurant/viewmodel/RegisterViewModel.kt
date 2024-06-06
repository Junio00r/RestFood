package com.devmobile.android.restaurant.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
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

    // Loading live data
    private val _loadingProgress = MutableLiveData<LoadState>()
    val loadingProgress = _loadingProgress

    companion object {
        const val VALID_DATA = "VALID"
    }

    fun register(
        userName: String?, userLastName: String? = "", userEmail: String?, userPassword: String?
    ) {

        if (isValidData(userName, userEmail, userPassword)) {

            _loadingProgress.value = LoadState.Loading

            val newUser = User(
                null,
                userName!!.trim(),
                userLastName?.matches(InputPatterns.TEXT_PATTERN.toRegex()).toString(),
                userEmail!!.trim(),
                userPassword!!.trim(),
            )

            viewModelScope.launch {

                try {

                    registerRepository.createAccount(newUser)

                    _loadingProgress.value = LoadState.NotLoading(true)

                } catch (e: Exception) {

                    Log.e(
                        "Teste classname: RegisterViewModel",
                        "Error Creating Account: ${e.message}"
                    )
                    _loadingProgress.value =
                        LoadState.Error(Throwable("Teste Error: Request timeout create account"))

                }
            }
        }
    }

    private fun isValidData(
        userName: String?, userEmail: String?, userPassword: String?
    ): Boolean {

        var result = true

        if (!InputPatterns.matcher(InputPatterns.TEXT_PATTERN, userName)) {

            _userNameError.value = "Invalid Name."
            result = false

        } else {
            _userNameError.value = VALID_DATA
        }

        if (!InputPatterns.matcher(InputPatterns.EMAIL_PATTERN, userEmail)) {

            _userEmailError.value = "Invalid Email."
            result = false

        } else {
            _userEmailError.value = VALID_DATA
        }

        if (!InputPatterns.matcher(InputPatterns.PASSWORD_PATTERN, userPassword)) {

            _userPasswordError.value =
                "Password have must in minimum 8 characters, three numbers and at least one special character ($,*, -)."
            result = false

        } else {
            _userPasswordError.value = VALID_DATA
        }

        return result
    }
}