package com.devmobile.android.restaurant.viewmodel.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.InputPatterns
import com.devmobile.android.restaurant.model.repository.authentication.VerificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class VerificationViewModel(
    private val repository: VerificationRepository, /* It's not recommended because repository have a Context dependency */
    private val handleUIState: SavedStateHandle,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
) : ViewModel() {

    // UIState
    private val code1: String
        get() = handleUIState["NUMBER_1"] ?: ""
    private val code2: String
        get() = handleUIState["NUMBER_2"] ?: ""
    private val code3: String
        get() = handleUIState["NUMBER_3"] ?: ""
    private val code4: String
        get() = handleUIState["NUMBER_4"] ?: ""
    private val code5: String
        get() = handleUIState["NUMBER_5"] ?: ""
    private val code6: String
        get() = handleUIState["NUMBER_6"] ?: ""

    fun saveCode1(newCode: String) {
        handleUIState["NUMBER_1"] = newCode
    }
    fun saveCode2(newCode: String) {
        handleUIState["NUMBER_2"] = newCode
    }
    fun saveCode3(newCode: String) {
        handleUIState["NUMBER_3"] = newCode
    }
    fun saveCode4(newCode: String) {
        handleUIState["NUMBER_4"] = newCode
    }
    fun saveCode5(newCode: String) {
        handleUIState["NUMBER_5"] = newCode
    }
    fun saveCode6(newCode: String) {
        handleUIState["NUMBER_6"] = newCode
    }

    // observables
    private val _codeErrorPropagator = MutableLiveData<String?>()
    val codeErrorPropagator: LiveData<String?> = _codeErrorPropagator

    private val _canStillEnterCodes = MutableLiveData<Boolean>()
    val canStillEnterCodes: LiveData<Boolean> = _canStillEnterCodes

    private val _canResendCode = MutableLiveData<Boolean>()
    val canResendCode: LiveData<Boolean> = _canResendCode

    // toggles
    private val _requestForResendCode = MutableSharedFlow<Unit>()
    private val _sendCodesInserted = MutableSharedFlow<Unit>()

    init {

        // coroutines scope on init block isn't recommended
        coroutineScope.launch {

            _requestForResendCode.debounce(1000).collect {

                handleResendVerificationCode()
            }
        }

        coroutineScope.launch {

            _sendCodesInserted.debounce(1000).collect {

                handleCodeInserted(arrayListOf(code1, code2, code3, code4, code5, code6))
            }
        }
    }

    // triggers
    @CalledFromXML
    fun resendCodeTrigger() {

        coroutineScope.launch {

            _requestForResendCode.emit(Unit)
        }
    }

    fun sendCodesInsertedTrigger() {

        coroutineScope.launch {

            _sendCodesInserted.emit(Unit)
        }
    }

    // functions
    private fun <T : Collection<String>> handleCodeInserted(numbers: T) {

        when (isNumberValid(numbers) and isCodeValid(numbers)) {

            true  -> _canStillEnterCodes.value = false

            false -> _codeErrorPropagator.value = "Error: Code Incorrect"
        }
    }

    private fun handleResendVerificationCode() {

        if (_canResendCode.value == true || _canResendCode.value == null) {

            _canStillEnterCodes.value = true
            _canResendCode.value = false

            repository.requestNewVerificationCode()

            // Simulation time for request code
            viewModelScope.launch {

                delay(6000).let {

                    _canResendCode.value = true
                }
            }
        }
    }

    private fun <T : Collection<String>> isNumberValid(numbersEntered: T): Boolean {

        numbersEntered.forEach { code ->

            val check = InputPatterns.isMatch(InputPatterns.NUMBER_PATTERN, code)

            if (!check.first) {

                return false
            }
        }

        return true
    }

    private fun <T : Collection<String>> isCodeValid(numbersEntered: T): Boolean {

        return repository.isCodeValid(numbersEntered.joinToString(separator = ""))
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScope.cancel("Finishing ViewModel")
    }
}