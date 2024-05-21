package com.devmobile.android.restaurant.model.repository

import com.devmobile.android.restaurant.model.entities.User

sealed class AuthenticationResult {
    data class Success(val user: User) : AuthenticationResult()
    data class Error(val exception: Exception) : AuthenticationResult()
}