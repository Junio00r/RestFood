package com.devmobile.android.restaurant.viewmodel.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.model.repository.authentication.VerificationRepository
import com.devmobile.android.restaurant.viewmodel.InputPatterns
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class VerificationViewModel(
    private val repository: VerificationRepository,
    private val handleUIState: SavedStateHandle
) : ViewModel() {

    // UIState
    private val code1: String
        get() = handleUIState["CODE_1"] ?: ""
    private val code2: String
        get() = handleUIState["CODE_2"] ?: ""
    private val code3: String
        get() = handleUIState["CODE_3"] ?: ""
    private val code4: String
        get() = handleUIState["CODE_4"] ?: ""
    private val code5: String
        get() = handleUIState["CODE_5"] ?: ""
    private val code6: String
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

    private val _canStillEnterCodes = MutableLiveData<Boolean>()
    val canStillEnterCodes: LiveData<Boolean> = _canStillEnterCodes

    private val _codeErrorPropagator = MutableLiveData<String?>()
    val codeErrorPropagator: LiveData<String?> = _codeErrorPropagator

    private val _canResendCode = MutableLiveData<Boolean>()
    val canResendCode: LiveData<Boolean> = _canResendCode

    private val _requestForResendCode = MutableSharedFlow<Unit>()

    init {

        // coroutines scope on init block isn't recommended
        viewModelScope.launch {

            _requestForResendCode.debounce(1000).collect {

                if (_canResendCode.value == true || _canResendCode.value == null) {

                    _canResendCode.value = false

                    requestNewCodeForValidation()

                    delay(60000).let {

                        _canStillEnterCodes.value = true
                        _canResendCode.value = true
                    }
                }
            }
        }
    }

    // Functions
    @CalledFromXML
    fun resendCodeTrigger() {

        viewModelScope.launch {

            _requestForResendCode.emit(Unit)
        }
    }

    fun <T: Collection<String>> requestCodesValidation(codesEntered: T) {

        when (isCodePatternValid(codesEntered)) {

            true -> {

                viewModelScope.launch {

                    _canStillEnterCodes.value = false
                    repository.validCodes()
                }
            }

            false -> {

                _codeErrorPropagator.value = "Error code incorrect"
            }
        }
    }

    private fun <T: Collection<String>> isCodePatternValid(codesEntered: T): Boolean {

        codesEntered.forEach { code ->

            val check = InputPatterns.isMatch(InputPatterns.NUMBER_PATTERN, code)

            if (!check.first) {

                return false
            }
        }

        return true
    }

    private fun requestNewCodeForValidation() {

    }
}