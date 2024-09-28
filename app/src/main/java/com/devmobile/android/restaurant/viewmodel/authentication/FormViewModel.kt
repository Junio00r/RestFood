package com.devmobile.android.restaurant.viewmodel.authentication

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.usecase.InputPatterns
import com.devmobile.android.restaurant.RequestResult
import com.devmobile.android.restaurant.model.repository.authentication.FormRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@OptIn(FlowPreview::class)
class FormViewModel(
    private val registerRepository: FormRepository,
    private val handleUIState: SavedStateHandle,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
) : ViewModel() {

    companion object {

        fun provideFactory(
            repository: FormRepository,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {

                    return FormViewModel(
                        registerRepository = repository,
                        handleUIState = handle,
                    ) as T
                }
            }
    }

    // UIState
    val userName: String
        get() = handleUIState["NAME"] ?: ""
    val userLastName: String
        get() = handleUIState["LAST_NAME"] ?: ""
    val userEmail: String
        get() = handleUIState["EMAIL"] ?: ""
    val userPassword: String
        get() = handleUIState["PASSWORD"] ?: ""

    fun onNameChanged(newName: String) {
        handleUIState["NAME"] = newName
    }

    fun onLastNameChanged(newLastName: String) {
        handleUIState["LAST_NAME"] = newLastName
    }

    fun onEmailChanged(newEmail: String) {
        handleUIState["EMAIL"] = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        handleUIState["PASSWORD"] = newPassword
    }

    // Errors
    private val _nameErrorPropagator = MutableLiveData<String?>()
    val nameErrorPropagator: LiveData<String?> = _nameErrorPropagator

    private val _lastNameErrorPropagator = MutableLiveData<String?>()
    val lastNameErrorPropagator: LiveData<String?> = _lastNameErrorPropagator

    private val _emailErrorPropagator = MutableLiveData<String?>()
    val emailErrorPropagator: LiveData<String?> = _emailErrorPropagator

    private val _passwordErrorPropagator = MutableLiveData<String?>()
    val passwordErrorPropagator: LiveData<String?> = _passwordErrorPropagator

    // SharedFlow because LiveData or StateFlow sends latest value when new collectors
    // are subscribers, i.e., in cases of changes configurations this isn't good.
    private val _resultRequestData: MutableSharedFlow<RequestResult> = MutableSharedFlow()
    val resultRequestData: SharedFlow<RequestResult> = _resultRequestData

    // To Debounce Pattern
    private val _registerDebounceFlow: MutableSharedFlow<Unit> = MutableSharedFlow()

    init {

        // Scopes on init block isn't recommended
        coroutineScope.launch {

            // debounce Flow for register new user
            _registerDebounceFlow.debounce(500).collect {

                requestUserData(userName, userLastName, userEmail, userPassword)
            }
        }
    }

    fun registerTrigger() {

        coroutineScope.launch {

            _registerDebounceFlow.emit(Unit)
        }
    }

    private fun requestUserData(
        userName: String?, userLastName: String? = "", userEmail: String?, userPassword: String?
    ) {

        // It may synchronized
        if (isValidData(userName, userLastName, userEmail, userPassword)) {

            coroutineScope.launch {

                try {

                    registerRepository.hasEmailAlreadyRegistered(userEmail!!)



                    _resultRequestData.emit(RequestResult.Success())

                } catch (e: Exception) {

                    _resultRequestData.emit(RequestResult.Error(java.lang.Exception("It was not possible create account")))
                }
            }
        }
    }

    private fun isValidData(
        userName: String?, userLastName: String?, userEmail: String?, userPassword: String?
    ): Boolean {

        val isNameValid = isDataRequiredValid(
            data = userName,
            inputPattern = InputPatterns.TEXT_PATTERN,
            errorPropagator = _nameErrorPropagator
        )

        val isLastNameValid = isDataOptionalValid(
            data = userLastName,
            inputPattern = InputPatterns.TEXT_PATTERN,
            errorPropagator = _lastNameErrorPropagator
        )

        val isEmailValid = isDataRequiredValid(
            data = userEmail,
            inputPattern = InputPatterns.EMAIL_PATTERN,
            errorPropagator = _emailErrorPropagator
        )

        val isPassword = isDataRequiredValid(
            data = userPassword,
            inputPattern = InputPatterns.PASSWORD_PATTERN,
            errorPropagator = _passwordErrorPropagator
        )

        return isNameValid and isLastNameValid and isEmailValid and isPassword
    }

    private fun isDataRequiredValid(
        data: String?, inputPattern: Pattern, errorPropagator: MutableLiveData<String?>
    ): Boolean {

        val isValid = InputPatterns.isMatch(inputPattern, data)

        if (isValid.first) {

            errorPropagator.value = null
            return true
        }

        errorPropagator.value = isValid.second
        return false
    }

    private fun isDataOptionalValid(
        data: String?, inputPattern: Pattern, errorPropagator: MutableLiveData<String?>
    ): Boolean {

        val isValid = InputPatterns.isMatch(inputPattern, data)

        if (data.isNullOrEmpty() or isValid.first) {

            errorPropagator.value = null
            return true
        }

        errorPropagator.value = isValid.second
        return false
    }
}
