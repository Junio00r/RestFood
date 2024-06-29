package com.devmobile.android.restaurant.viewmodel

import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.devmobile.android.restaurant.AccountException
import com.devmobile.android.restaurant.InputPatterns
import com.devmobile.android.restaurant.model.repository.remotedata.FormRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.regex.Pattern

@OptIn(FlowPreview::class)
class FormViewModel(
    private val registerRepository: FormRepository, private val handleUIState: SavedStateHandle
) : ViewModel() {

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

    fun onLastNameChanged(newName: String) {
        handleUIState["LAST_NAME"] = newName
    }

    fun onEmailChanged(newName: String) {
        handleUIState["EMAIL"] = newName
    }

    fun onPasswordChanged(newName: String) {
        handleUIState["PASSWORD"] = newName
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


    // Loading Live Data
    private val _loadingProgress = MutableLiveData<LoadState>()
    val loadingProgress: LiveData<LoadState> = _loadingProgress

    // For Debounce Pattern
    private val _registerDebounceFlow = MutableSharedFlow<Unit?>()

    init {

        // Scopes on init block isn't recommended
        viewModelScope.launch {

            // debounce Flow for register new user
            _registerDebounceFlow.debounce(500).collect {

                requestUserData(userName, userLastName, userEmail, userPassword)
            }
        }
    }

    fun registerTrigger() {
        viewModelScope.launch {
            _registerDebounceFlow.emit(Unit)
        }
    }

    private fun requestUserData(
        userName: String?, userLastName: String? = "", userEmail: String?, userPassword: String?
    ) {


        if (isValidData(userName, userLastName, userEmail, userPassword)) {

            viewModelScope.launch {

                try {

                    if (!registerRepository.hasEmailAlreadyRegistered(email = userEmail!!)) {
                        _loadingProgress.value = LoadState.Loading
                    }

                } catch (e: IOException) {

                    _loadingProgress.value =
                        LoadState.Error(Throwable("It was not possible create account"))

                } catch (e: SQLiteDatabaseCorruptException) {

                    _loadingProgress.value =
                        LoadState.Error(Throwable("It was not possible create account"))

                } catch (e: SQLiteException) {

                    _loadingProgress.value =
                        LoadState.Error(Throwable("It was not possible create account"))

                } catch (e: AccountException) {

                    _emailErrorPropagator.value = "Email is invalid or already taken"
                    _loadingProgress.value =
                        LoadState.Error(Throwable("Email is invalid or already taken"))
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
