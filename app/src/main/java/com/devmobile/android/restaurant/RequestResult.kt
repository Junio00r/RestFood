package com.devmobile.android.restaurant

import com.devmobile.android.restaurant.model.entities.User

sealed class RequestResult {
    data class Success(val user: User) : RequestResult()
    data class Error(val exception: Exception) : RequestResult()
}