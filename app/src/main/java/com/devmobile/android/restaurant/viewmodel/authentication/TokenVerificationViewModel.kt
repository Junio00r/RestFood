package com.devmobile.android.restaurant.viewmodel.authentication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.devmobile.android.restaurant.usecase.CalledFromXML
import com.devmobile.android.restaurant.usecase.RequestResult
import com.devmobile.android.restaurant.model.datasource.local.entities.User
import com.devmobile.android.restaurant.model.repository.TokenVerificationRepository
import com.devmobile.android.restaurant.model.datasource.remote.EmailRequest
import com.devmobile.android.restaurant.model.datasource.remote.Sender
import com.devmobile.android.restaurant.model.datasource.remote.To
import com.devmobile.android.restaurant.usecase.InputPatterns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(FlowPreview::class)
class TokenVerificationViewModel(
    private val repository: TokenVerificationRepository, /* It's not recommended because repository have a Context dependency */
    private val handleUIState: SavedStateHandle,
    private val coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main),
    private val userData: Collection<String>,
    private val templateWithoutCodeDefined: String
) : ViewModel() {

    companion object {

        fun provideFactory(
            repository: TokenVerificationRepository,
            owner: SavedStateRegistryOwner,
            userData: Collection<String>,
            templateWithoutCodeDefined: String,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String, modelClass: Class<T>, handle: SavedStateHandle
                ): T {

                    return TokenVerificationViewModel(
                        repository = repository,
                        handleUIState = handle,
                        userData = userData,
                        templateWithoutCodeDefined = templateWithoutCodeDefined,
                    ) as T
                }
            }
    }

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
    private val _canStillEnterCodes = MutableLiveData<Boolean>()
    val canStillEnterCodes: LiveData<Boolean> = _canStillEnterCodes

    private val _canResendCode: MutableLiveData<Boolean> = MutableLiveData(true)
    val canResendCode: LiveData<Boolean> = _canResendCode

    private val _resultRequestCreateAcc: MutableSharedFlow<RequestResult> = MutableSharedFlow()
    val resultRequestCreateAcc: SharedFlow<RequestResult> = _resultRequestCreateAcc

    // toggles
    private val _requestForResendCode = MutableSharedFlow<Unit>()
    private val _sendCodesInserted = MutableSharedFlow<Unit>()

    init {

        coroutineScope.launch {

            repository.requestNewVerificationCode(
                EmailRequest(
                    sender = Sender("RestFood", "devcodeandcoffee@gmail.com"),
                    to = listOf(To(email = userData.elementAt(2))),
                    htmlContent = prepareVerificationTemplate(),
                    subject = "${CodeGenerator.currentCodeGenerated()} é o seu código de acesso",
                )
            )
        }

        // coroutines scope on init block isn't recommended
        coroutineScope.launch {

            _requestForResendCode.debounce(800).collect {

                handleResendVerificationCode()
            }
        }

        coroutineScope.launch {
            _sendCodesInserted.debounce(800).collect {

                try {

                    handleCodeInserted(
                        arrayListOf(number1, number2, number3, number4, number5, number6)
                    )
                    _resultRequestCreateAcc.emit(RequestResult.Success())

                } catch (e: Exception) {

                    _resultRequestCreateAcc.emit(RequestResult.Error(Exception("Code Incorrect or isn't possible create account")))
                }
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
    private suspend fun <T : Collection<String>> handleCodeInserted(numbers: T) {

        if (isNumbersValid(numbers) and checkCode(numbers.joinToString(separator = ""))) {

            _canStillEnterCodes.value = false

            repository.createAccount(
                user = User(
                    name = userData.elementAt(0),
                    lastname = userData.elementAt(1),
                    email = userData.elementAt(2),
                    password = userData.elementAt(3)
                )
            )
        } else {

            throw Exception()
        }
    }

    private fun handleResendVerificationCode() {

        if (canResendCode.value == true) {

            _canStillEnterCodes.value = true
            _canResendCode.value = false

            coroutineScope.launch {

                repository.requestNewVerificationCode(
                    EmailRequest(
                        sender = Sender("RestFood", "devcodeandcoffee@gmail.com"),
                        to = listOf(To(email = userData.elementAt(2))),
                        htmlContent = prepareVerificationTemplate(),
                        subject = "${CodeGenerator.currentCodeGenerated()} é o seu código de acesso",
                    )
                )
                _canResendCode.value = true
            }
        }
    }

    private fun <T : Collection<String>> isNumbersValid(numbersEntered: T): Boolean {

        numbersEntered.forEach { code ->

            val check = InputPatterns.isMatch(InputPatterns.NUMBER_PATTERN, code)

            if (!check.isMatch) {

                return false
            }
        }

        return true
    }

    private fun checkCode(codeEntered: String): Boolean {

        return codeEntered == CodeGenerator.currentCodeGenerated()
    }

    private suspend fun prepareVerificationTemplate(): String {

        return coroutineScope.async(Dispatchers.Default) {

            templateWithoutCodeDefined.replace("VALIDATION_CODE", CodeGenerator.generateCode())
        }.await()
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScope.cancel("Finishing ViewModel")
    }
}

internal object CodeGenerator {

    private var generator: Random = Random.Default
    private var currentCodeGenerated: String? = null

    @SuppressLint("DefaultLocale")
    fun generateCode(): String {

        currentCodeGenerated = String.format("%06d", generator.nextInt(0, 1_000_000))

        return currentCodeGenerated as String
    }

    fun currentCodeGenerated(): String {

        return currentCodeGenerated ?: generateCode()
    }
}