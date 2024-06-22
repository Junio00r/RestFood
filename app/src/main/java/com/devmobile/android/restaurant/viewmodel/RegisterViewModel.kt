package com.devmobile.android.restaurant.viewmodel

import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.devmobile.android.restaurant.AccountException
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.InputPatterns
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.io.IOException

data class RegisterUIState(
    val name: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null
)

@OptIn(FlowPreview::class)
class RegisterViewModel(
    private val registerRepository: RegisterRepository,
    private val uiState: SavedStateHandle
) : ViewModel() {

    // UIState
    val userName: String
        get() = uiState["NAME"] ?: ""
    val userLastName: String
        get() = uiState["LAST_NAME"] ?: ""
    val userEmail: String
        get() = uiState["EMAIL"] ?: ""
    val userPassword: String
        get() = uiState["PASSWORD"] ?: ""

    fun onNameChanged(newName: String) {
        uiState["NAME"] = newName
    }

    fun onLastNameChanged(newName: String) {
        uiState["LAST_NAME"] = newName
    }

    fun onEmailChanged(newName: String) {
        uiState["EMAIL"] = newName
    }

    fun onPasswordChanged(newName: String) {
        uiState["PASSWORD"] = newName
    }

    // Errors
    private val _userNameError = MutableLiveData<String?>()
    val userNameError: LiveData<String?> = _userNameError

    private val _userLastNameError = MutableLiveData<String?>()
    val userLastNameError: LiveData<String?> = _userLastNameError

    private val _userEmailError = MutableLiveData<String?>()
    val userEmailError: LiveData<String?> = _userEmailError

    private val _userPasswordError = MutableLiveData<String?>()
    val userPasswordError: LiveData<String?> = _userPasswordError


    // Loading Live Data
    private val _loadingProgress = MutableLiveData<LoadState>()
    val loadingProgress: LiveData<LoadState> = _loadingProgress

    // For Debounce Pattern
    private val _registerDebounceFlow = MutableSharedFlow<Unit?>()

    // RegisterFragment UI State
    private val _registerUIState = MutableStateFlow(RegisterUIState())

    companion object {
        const val VALID_DATA = "VALID"
    }

    init {

        // Scopes on init block isn't recommended
        viewModelScope.launch {

            // debounce Flow for register new user
            _registerDebounceFlow.debounce(500).collect {

                with(_registerUIState.value) {
                    register(name, lastName, email, password)
                }
            }
        }
    }

    private fun register(
        userName: String?, userLastName: String? = "", userEmail: String?, userPassword: String?
    ) {

        if (isValidData(userName, userLastName, userEmail, userPassword)) {

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

                } catch (e: AccountException) {

                    _userEmailError.value = "Test Email is invalid or already taken"
                    _loadingProgress.value =
                        LoadState.Error(Throwable("Test Email is invalid or already taken"))

                } catch (e: IOException) {

                    _loadingProgress.value =
                        LoadState.Error(Throwable("Test It was not possible create account"))

                } catch (e: SQLiteDatabaseCorruptException) {

                    _loadingProgress.value =
                        LoadState.Error(Throwable("Test It was not possible create account"))

                } catch (e: SQLiteException) {

                    _loadingProgress.value =
                        LoadState.Error(Throwable("Test It was not possible create account"))

                } catch (e: Exception) {

                    _loadingProgress.value =
                        LoadState.Error(Throwable("Test It was not possible create account"))
                }
            }
        }
    }

    private fun isValidData(
        userName: String?, userLastName: String?, userEmail: String?, userPassword: String?
    ): Boolean {

        var result = true

        if (InputPatterns.isMatch(InputPatterns.TEXT_PATTERN, userName)) {

            _userNameError.value = VALID_DATA
        } else {
            _userNameError.value = InputPatterns.TEXT_NAME_ERROR_MESSAGE
            result = false
        }

        if (userLastName != null) {

            Log.d("ViewModel", "Email dahfflhjajfjk")
            if (InputPatterns.isMatch(InputPatterns.TEXT_PATTERN, userLastName)) {

                _userNameError.value = VALID_DATA
            } else {
                _userNameError.value = InputPatterns.TEXT_NAME_ERROR_MESSAGE
                result = false
            }
        }

        if (InputPatterns.isMatch(InputPatterns.EMAIL_PATTERN, userEmail)) {

            _userEmailError.value = VALID_DATA
        } else {
            _userEmailError.value = InputPatterns.EMAIL_ERROR_MESSAGE
            result = false
        }

        if (InputPatterns.isMatch(InputPatterns.PASSWORD_PATTERN, userPassword)) {

            _userPasswordError.value = VALID_DATA

        } else {
            _userPasswordError.value = InputPatterns.PASSWORD_ERROR_MESSAGE
            result = false
        }

        return result
    }

    fun registerTrigger() {
        viewModelScope.launch {
            _registerDebounceFlow.emit(Unit)
        }
    }
}
