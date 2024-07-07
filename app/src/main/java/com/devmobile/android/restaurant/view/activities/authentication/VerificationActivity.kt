package com.devmobile.android.restaurant.view.activities.authentication

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.devmobile.android.restaurant.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.ActivityVerificationCodeBinding
import com.devmobile.android.restaurant.model.repository.remotedata.VerificationRepository
import com.devmobile.android.restaurant.view.customelements.TextInput
import com.devmobile.android.restaurant.viewmodel.VerificationViewModel
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class VerificationActivity : AppCompatActivity(), IShowError, LifecycleEventObserver {

    private lateinit var _verificationBinding: ActivityVerificationCodeBinding

    private val _repository = VerificationRepository(this)

    private val _verificationViewModel: VerificationViewModel by viewModels {

        ViewModelFactory(
            repository = _repository, ownerOfStateToSave = this, defaultValuesForNulls = null
        )
    }

    private val _codes = ArrayList<TextInput>()

    init {
        lifecycle.addObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _verificationBinding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(_verificationBinding.root)

        _verificationBinding.viewModel = _verificationViewModel
        _verificationBinding.activity = this


        with(_verificationBinding) {

            _codes.addAll(
                arrayOf(
                    code1.textInputEditText,
                    code2.textInputEditText,
                    code3.textInputEditText,
                    code4.textInputEditText,
                    code5.textInputEditText,
                    code6.textInputEditText
                )
            )

            _codes.forEach { code ->

                // InputType
                code.inputType = InputType.TYPE_CLASS_NUMBER

                // Text Alignment
                code.gravity = Gravity.CENTER

                // Max Chars in Text
                code.maxLength(1)
            }

            // Layout text size
            code1.textInputForm.layoutParams.height = 300
            code2.textInputForm.layoutParams.height = 300
            code3.textInputForm.layoutParams.height = 300
            code4.textInputForm.layoutParams.height = 300
            code5.textInputForm.layoutParams.height = 300
            code6.textInputForm.layoutParams.height = 300

            setFocus()
        }
    }

    private fun setObservables() {

        with(_verificationBinding) {

            code1.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode1(it.toString())
                sendCode()
                setFocus()
            }

            code2.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode2(it.toString())
                sendCode()
                setFocus()
            }

            code3.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode3(it.toString())
                sendCode()
                setFocus()
            }

            code4.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode4(it.toString())
                sendCode()
                setFocus()
            }

            code5.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode5(it.toString())
                sendCode()
                setFocus()
            }

            code6.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode6(it.toString())
                setFocus()
                sendCode()
            }

            _verificationViewModel.isEnableInput.observe(this@VerificationActivity) { mayEnableInput ->

                mayFocusable(mayEnableInput)
            }

            _verificationViewModel.codeErrorPropagator.observe(this@VerificationActivity) { error ->

                if (error.isNullOrEmpty()) {

                    showErrorMessage(error!!)
                }
            }

            _verificationViewModel.canResendCode.observe(this@VerificationActivity) { canResendCode ->

                changedResendTextColor(canResendCode)
                clearInput()
            }
        }
    }

    private fun setFocus() {

        _codes.firstOrNull { it.text.isNullOrEmpty() }?.requestFocus()
    }

    private fun mayFocusable(isFocusable: Boolean) {

        _codes.forEach {

            if (isFocusable) {

                it.isFocusable = true
                it.isFocusableInTouchMode = true

            } else {

                it.isFocusable = false
                it.isFocusableInTouchMode = false
            }
        }
    }

    private fun clearInput() {

        _codes.forEach {

            it.text?.clear()
        }
    }

    private fun sendCode() {

        _verificationViewModel.codeVerify(
            codesEntered = arrayOf(
                _codes[0].text.toString(),
                _codes[1].text.toString(),
                _codes[2].text.toString(),
                _codes[3].text.toString(),
                _codes[4].text.toString(),
                _codes[5].text.toString(),
            )
        )
    }

    private fun changedResendTextColor(wasResendCode: Boolean) {

        if (wasResendCode) {

            _verificationBinding.textResendCode.setTextColor(
                ColorStateList.valueOf(
                    getColor(
                        R.color.black_two
                    )
                )
            )

        } else {

            _verificationBinding.textResendCode.setTextColor(
                ColorStateList.valueOf(
                    getColor(
                        R.color.gray_two
                    )
                )
            )
        }
    }

    private fun rememberUIState() {

        val codesForRemember = listOf(
            Pair(_codes[0], _verificationViewModel.code1),
            Pair(_codes[1], _verificationViewModel.code2),
            Pair(_codes[2], _verificationViewModel.code3),
            Pair(_codes[3], _verificationViewModel.code4),
            Pair(_codes[4], _verificationViewModel.code5),
            Pair(_codes[5], _verificationViewModel.code6)
        )

        codesForRemember.forEach { pair ->

            if (pair.second.isNotEmpty()) {

                pair.first.setText(pair.second)
            }
        }
    }

    override fun showErrorMessage(errorMessage: String) {

        val mySnackbar = Snackbar.make(_verificationBinding.container, errorMessage, 2000)

        mySnackbar.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.orange)))
        mySnackbar.show()
    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

        when (event) {

            Lifecycle.Event.ON_CREATE -> {

            }

            Lifecycle.Event.ON_START -> {

            }

            Lifecycle.Event.ON_RESUME -> {

                setObservables()
                rememberUIState()
            }

            Lifecycle.Event.ON_PAUSE -> {

            }

            Lifecycle.Event.ON_STOP -> {

            }

            Lifecycle.Event.ON_DESTROY -> {

            }

            Lifecycle.Event.ON_ANY -> {

            }
        }
    }
}