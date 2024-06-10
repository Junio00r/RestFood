package com.devmobile.android.restaurant.viewmodel

import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import com.devmobile.android.restaurant.model.AccountException
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.InputPatterns
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

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

}