package com.devmobile.android.restaurant.viewmodel.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.InputPatterns
import com.devmobile.android.restaurant.model.repository.authentication.TokenVerificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class TokenVerificationViewModel(
    private val repository: TokenVerificationRepository, /* It's not recommended because repository have a Context dependency */
    private val handleUIState: SavedStateHandle,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate),
) : ViewModel() {

    // UIState
    val number1: String
        get() = handleUIState["NUMBER_1"] ?: ""
    val number2: String
        get() = handleUIState["NUMBER_2"] ?: ""
    val number3: String
        get() = handleUIState["NUMBER_3"] ?: ""
    val number4: String
        get() = handleUIState["NUMBER_4"] ?: ""
    val number5: String
        get() = handleUIState["NUMBER_5"] ?: ""
    val number6: String
        get() = handleUIState["NUMBER_6"] ?: ""

    fun saveNumber1(newCode: String) {
        handleUIState["NUMBER_1"] = newCode
    }

    fun saveNumber2(newCode: String) {
        handleUIState["NUMBER_2"] = newCode
    }

    fun saveNumber3(newCode: String) {
        handleUIState["NUMBER_3"] = newCode
    }

    fun saveNumber4(newCode: String) {
        handleUIState["NUMBER_4"] = newCode
    }

    fun saveNumber5(newCode: String) {
        handleUIState["NUMBER_5"] = newCode
    }

    fun saveNumber6(newCode: String) {
        handleUIState["NUMBER_6"] = newCode
    }

    // observables
    private val _codeErrorPropagator = MutableLiveData<String?>()
    val codeErrorPropagator: LiveData<String?> = _codeErrorPropagator

    private val _canStillEnterCodes = MutableLiveData<Boolean>()
    val canStillEnterCodes: LiveData<Boolean> = _canStillEnterCodes

    private val _canResendCode: MutableLiveData<Boolean> = MutableLiveData(true)
    val canResendCode: LiveData<Boolean> = _canResendCode

    // toggles
    private val _requestForResendCode = MutableSharedFlow<Unit>()
    private val _sendCodesInserted = MutableSharedFlow<Unit>()

    init {

        // coroutines scope on init block isn't recommended
        coroutineScope.launch {

            _requestForResendCode.debounce(800).collect {

                handleResendVerificationCode()
            }
        }

        coroutineScope.launch {

            _sendCodesInserted.debounce(800).collect {

                handleCodeInserted(
                    arrayListOf(
                        number1,
                        number2,
                        number3,
                        number4,
                        number5,
                        number6
                    )
                )
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

        when (isNumbersValid(numbers) and repository.checkCode(numbers.joinToString(separator = ""))) {

            true -> {

                _canStillEnterCodes.value = false

                coroutineScope.launch {

                    repository.createAccount()
                }
            }

            false -> {

                _codeErrorPropagator.value = "Error: Code Incorrect"
            }
        }
    }

    private fun handleResendVerificationCode() {

        if (canResendCode.value == true) {

            _canStillEnterCodes.value = true
            _canResendCode.value = false

            coroutineScope.launch {

                repository.requestNewVerificationCode()
                _canResendCode.value = true
            }
        }
    }

    private fun <T : Collection<String>> isNumbersValid(numbersEntered: T): Boolean {

        numbersEntered.forEach { code ->

            val check = InputPatterns.isMatch(InputPatterns.NUMBER_PATTERN, code)

            if (!check.first) {

                return false
            }
        }

        return true
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScope.cancel("Finishing ViewModel")
    }
}