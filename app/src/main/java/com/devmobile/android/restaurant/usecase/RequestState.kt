package com.devmobile.android.restaurant.usecase

sealed class RequestResult {
    data class Success(val message: String? = null) : RequestResult()
    data class Error(val exception: Exception) : RequestResult()
}