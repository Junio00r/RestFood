package com.devmobile.android.restaurant.viewmodel

import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.devmobile.android.restaurant.model.AccountException
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.InputPatterns
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class RegisterUIState(
    var name: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null
)

@OptIn(FlowPreview::class)
class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    // RegisterFragment UI State
    private val _registerUIState = MutableStateFlow(RegisterUIState())
    val registerUIState: StateFlow<RegisterUIState> = _registerUIState.asStateFlow()

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
        userName: String?, userEmail: String?, userPassword: String?
    ): Boolean {

        var result = true

        if (InputPatterns.isMatch(InputPatterns.TEXT_PATTERN, userName)) {

            _userNameError.value = VALID_DATA
        } else {
            _userNameError.value = InputPatterns.TEXT_NAME_ERROR_MESSAGE
            result = false
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

    fun updateUIState(
        newName: String? = null, newLastName: String? = null, newEmail: String? = null, newPassword: String? = null
    ) {
        _registerUIState.value = _registerUIState.value.copy(
            name = newName ?: _registerUIState.value.name,
            lastName = newLastName ?: _registerUIState.value.lastName,
            email = newEmail ?: _registerUIState.value.email,
            password = newPassword ?: _registerUIState.value.password
        )
    }

    fun registerTrigger() {
        viewModelScope.launch {
            _registerDebounceFlow.emit(Unit)
        }
    }
}
