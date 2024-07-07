package com.devmobile.android.restaurant.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.model.repository.remotedata.VerificationRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class VerificationViewModel(
    private val repository: VerificationRepository,
    private val handleUIState: SavedStateHandle
) : ViewModel() {

    // UIState
    val code1: String
        get() = handleUIState["CODE_1"] ?: ""
    val code2: String
        get() = handleUIState["CODE_2"] ?: ""
    val code3: String
        get() = handleUIState["CODE_3"] ?: ""
    val code4: String
        get() = handleUIState["CODE_4"] ?: ""
    val code5: String
        get() = handleUIState["CODE_5"] ?: ""
    val code6: String
        get() = handleUIState["CODE_6"] ?: ""

    fun saveCode1(newCode: String) {
        handleUIState["CODE_1"] = newCode
    }

    fun saveCode2(newCode: String) {
        handleUIState["CODE_2"] = newCode
    }

    fun saveCode3(newCode: String) {
        handleUIState["CODE_3"] = newCode
    }

    fun saveCode4(newCode: String) {
        handleUIState["CODE_4"] = newCode
    }

    fun saveCode5(newCode: String) {
        handleUIState["CODE_5"] = newCode
    }

    fun saveCode6(newCode: String) {
        handleUIState["CODE_6"] = newCode
    }

    private val _isEnableInput = MutableLiveData<Boolean>()
    val isEnableInput: LiveData<Boolean> = _isEnableInput

    private val _canResendCode = MutableLiveData<Boolean>()
    val canResendCode: LiveData<Boolean> = _canResendCode

    private val _codeErrorPropagator = MutableLiveData<String?>()
    val codeErrorPropagator: LiveData<String?> = _codeErrorPropagator

    private val _resendCode = MutableSharedFlow<Unit>()

    init {

        // coroutines scope on init block isn't recommended
        viewModelScope.launch {
            _resendCode.debounce(1000).collect {

                _canResendCode.value = false
                _isEnableInput.value = true
                repository.verifyCodeEmail()
            }
        }
    }

    // Functions
    @CalledFromXML
    fun resendCodeTrigger() {

        viewModelScope.launch {
            _resendCode.emit(Unit)
        }
    }

    fun codeVerify(codesEntered: Array<String>) {

        if (isCodePatternValid(codesEntered)) {

            viewModelScope.launch {

                _isEnableInput.value = false
                repository.verifyCodeEmail()
                _isEnableInput.value = true
                _canResendCode.value = true
            }

        } else {

            _codeErrorPropagator.value = "Error code incorrect"
        }
    }

    private fun isCodePatternValid(codesEntered: Array<String>): Boolean {

        codesEntered.forEach { code ->

            val check = InputPatterns.isMatch(InputPatterns.NUMBER_PATTERN, code)

            if (!check.first) {

                return false
            }
        }

        return true
    }
}