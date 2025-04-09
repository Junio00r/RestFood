package com.devmobile.android.restaurant.viewmodel.authentication

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.usecase.RequestState
import com.devmobile.android.restaurant.model.repository.LoginRepository
import com.devmobile.android.restaurant.usecase.InputPatterns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val handleUiState: SavedStateHandle,
    private val coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
) : ViewModel() {

    companion object {

        fun provideFactory(
            repository: LoginRepository,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String, modelClass: Class<T>, handle: SavedStateHandle
                ): T {

                    return LoginViewModel(
                        loginRepository = repository, handleUiState = handle
                    ) as T
                }
            }
    }

    val userEmail: String
        get() = handleUiState["EMAIL"] ?: ""

    val userPassword: String
        get() = handleUiState["PASSWORD"] ?: ""

    fun onEmailChanged(newEmail: String) {
        handleUiState["EMAIL"] = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        handleUiState["PASSWORD"] = newPassword
    }

    private val _errorDataPropagator: MutableLiveData<String?> = MutableLiveData()
    val errorDataPropagator: LiveData<String?> = _errorDataPropagator

    private val _requestLoginResult: MutableSharedFlow<RequestResult> = MutableSharedFlow()
    val requestLoginResult: SharedFlow<RequestResult> = _requestLoginResult.asSharedFlow()

    // For debounce pattern
    private val _loginRequestControl: MutableSharedFlow<Unit> = MutableSharedFlow(replay = 1)

    init {

        coroutineScope.launch {

            _loginRequestControl.collect {
                login()
                delay(1000)
            }
        }
    }

    fun loginTrigger() {

        coroutineScope.launch {

            _loginRequestControl.tryEmit(Unit)
        }
    }

    private fun login() {

        if (isDataValid(userEmail, userPassword)) {

            coroutineScope.launch {

                try {

                    loginRepository.makeRequestLogin(userEmail, userPassword)
                    _requestLoginResult.emit(RequestResult.Success())

                } catch (e: Exception) {

                    _errorDataPropagator.value =
                        "It's not possible make login. Please, check your email and password!"
                    _requestLoginResult.emit(RequestResult.Error(Exception(e.message)))
                }
            }

        } else {

            _errorDataPropagator.value = "Email or password are invalids"
        }
    }


    private fun isDataValid(email: String?, password: String?): Boolean {

        val isEmailValid = InputPatterns.isMatch(InputPatterns.EMAIL_PATTERN, email)
        val isPasswordValid = InputPatterns.isMatch(InputPatterns.PASSWORD_PATTERN, password)

        return isEmailValid.isMatch and isPasswordValid.isMatch
    }

    override fun onCleared() {
        coroutineScope.cancel()

        super.onCleared()
    }
}