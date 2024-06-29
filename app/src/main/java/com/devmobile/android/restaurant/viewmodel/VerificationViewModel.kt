package com.devmobile.android.restaurant.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.devmobile.android.restaurant.model.repository.remotedata.VerificationRepository

class VerificationViewModel(
    private val verificationRepository: VerificationRepository,
    private val handleUIState: SavedStateHandle
) : ViewModel() {

    private val _isCodeValid = MutableLiveData<Boolean>()
    val isCodeValid: LiveData<Boolean> = _isCodeValid

    fun codeVerification(vararg codes: String) {

        _isCodeValid.value = false
    }
}