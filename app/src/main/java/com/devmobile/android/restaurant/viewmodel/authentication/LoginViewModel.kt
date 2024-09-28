package com.devmobile.android.restaurant.viewmodel.authentication

import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.RequestResult
import com.devmobile.android.restaurant.model.repository.authentication.LoginRepository
import com.devmobile.android.restaurant.usecase.InputPatterns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    companion object {

        fun provideFactory(
            repository: LoginRepository,
            owner: SavedStateRegistryOwner,
            coroutineScope: CoroutineScope? = null,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {

                    return LoginViewModel(
                        loginRepository = repository
                    ) as T
                }
            }
    }

    fun login(email: TextView, password: TextView): Boolean {
        val mEmail    = email.text.trim().toString()
        val mPassword = password.text.trim().toString()

        if (isValidEmail(mEmail) && isValidPassword(mPassword)) {

            viewModelScope.launch {

                val loginResult = loginRepository.login(mEmail, mPassword)

                when (loginResult) {

                    is RequestResult.Success -> {

                        println("Login Realizado Com Sucesso")
                    }

                    is RequestResult.Error -> {

                        println(loginResult.exception.message)
                    }
                }
            }

            return true
        }

        return false
    }


    private fun isValidEmail(email: String?): Boolean {

        return email?.trim() == email?.let { Patterns.EMAIL_ADDRESS.matcher(it).toString() }
    }

    private fun isValidPassword(password: String?): Boolean {

        return password?.trim() == password?.let {
            InputPatterns.PASSWORD_PATTERN.matcher(it).toString()
        }
    }
}