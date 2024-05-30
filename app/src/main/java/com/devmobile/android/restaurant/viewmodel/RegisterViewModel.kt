package com.devmobile.android.restaurant.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.InputPatterns
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import kotlinx.coroutines.launch
import java.sql.SQLTimeoutException

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
    ): Boolean {

        if (!isDataValids(userName, userLastName, userEmail, userPassword)) {

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

            try {

                registerRepository.register(newUser)

                // Apenas para teste
                loadingProgress.value = LoadState.Loading

            } catch (e: SQLTimeoutException) {

                // Apenas para teste
                loadingProgress.value = LoadState.Error(throw e)
            }

            // Apenas para teste
            loadingProgress.value = LoadState.NotLoading(true)
        }

        return true
    }

    private fun isDataValids(
        userName: String?, lastName: String?, userEmail: String?, userPassword: String?
    ): Boolean {

        if (!InputPatterns.matcher(InputPatterns.TEXT_PATTERN, userName)) {

            _userNameError.value = "Invalid Name."
        } else {
            _userNameError.value = VALID_DATA
        }

        if (!InputPatterns.matcher(InputPatterns.TEXT_PATTERN, lastName)) {

            _userNameError.value = "Invalid Name."
        } else {
            _userNameError.value = VALID_DATA
        }

        if (!InputPatterns.matcher(InputPatterns.EMAIL_PATTERN, userEmail)) {

            _userEmailError.value = "Invalid Email."
        } else {
            _userEmailError.value = VALID_DATA
        }

        if (!InputPatterns.matcher(InputPatterns.PASSWORD_PATTERN, userPassword)) {

            _userPasswordError.value =
                "Password have must in minimum 8 characters, three numbers and at least one special character ($,*, -)."
            return false
        } else {
            _userPasswordError.value = VALID_DATA
        }

        return true
    }
}