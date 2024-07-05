package com.devmobile.android.restaurant.view.activities.authentication

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
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

            // InputType
            code1.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
            code2.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
            code3.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
            code4.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
            code5.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
            code6.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER

            // Text alignment
            code1.textInputEditText.gravity = Gravity.CENTER
            code2.textInputEditText.gravity = Gravity.CENTER
            code3.textInputEditText.gravity = Gravity.CENTER
            code4.textInputEditText.gravity = Gravity.CENTER
            code5.textInputEditText.gravity = Gravity.CENTER
            code6.textInputEditText.gravity = Gravity.CENTER

            // Max chars in text
            code1.textInputEditText.maxLength(1)
            code2.textInputEditText.maxLength(1)
            code3.textInputEditText.maxLength(1)
            code4.textInputEditText.maxLength(1)
            code5.textInputEditText.maxLength(1)
            code6.textInputEditText.maxLength(1)

            // Layout text size
            code1.textInputForm.layoutParams.height = 300
            code2.textInputForm.layoutParams.height = 300
            code3.textInputForm.layoutParams.height = 300
            code4.textInputForm.layoutParams.height = 300
            code5.textInputForm.layoutParams.height = 300
            code6.textInputForm.layoutParams.height = 300

            code1.textInputEditText.requestFocus()
        }
    }

    private fun setObservables() {

        with(_verificationBinding) {

            // TextInput text
            code1.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode1(code1.textInputEditText.text.toString())
                if (it.toString().isNotEmpty()) {

                    focus(code2.textInputEditText)
                }
            }

            code2.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode2(code2.textInputEditText.text.toString())
                if (it.toString().isNotEmpty()) {

                    focus(code3.textInputEditText)
                }
            }

            code3.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode3(code3.textInputEditText.text.toString())
                if (it.toString().isNotEmpty()) {

                    focus(code4.textInputEditText)
                }
            }

            code4.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode4(code4.textInputEditText.text.toString())
                if (it.toString().isNotEmpty()) {

                    focus(code5.textInputEditText)
                }
            }

            code5.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode5(code5.textInputEditText.text.toString())
                if (it.toString().isNotEmpty()) {

                    focus(code6.textInputEditText)
                }
            }

            code6.textInputEditText.doAfterTextChanged {

                _verificationViewModel.saveCode6(code6.textInputEditText.text.toString())

                if (it.toString().isNotEmpty()) {

                    sendCode()
                    handleFocus()
                }
            }

            _verificationViewModel.codeErrorPropagator.observe(this@VerificationActivity) { error ->

                if (error.isNullOrEmpty()) {

                    showErrorMessage(error!!)
                }
            }

            _verificationViewModel.canResendCode.observe(this@VerificationActivity) { canResendCode ->

                changedResendTextColor(canResendCode)
            }
        }
    }

    private fun <T : EditText> focus(text: T?) {

        if (text != null) {
            if (text.length() == 0) {
                text.requestFocus()
            }
        }
    }

    private fun sendCode() {

        with(_verificationBinding) {

            val code11 = code1.textInputEditText.text.toString()
            val code22 = code2.textInputEditText.text.toString()
            val code33 = code3.textInputEditText.text.toString()
            val code44 = code4.textInputEditText.text.toString()
            val code55 = code5.textInputEditText.text.toString()
            val code66 = code6.textInputEditText.text.toString()

            _verificationViewModel.codeVerify(
                codesEntered = arrayOf(code11, code22, code33, code44, code55, code66)
            )
        }
    }

    private fun handleFocus() {

        with(_verificationBinding) {

            code1.textInputEditText.isFocusable = false
            code2.textInputEditText.isFocusable = false
            code3.textInputEditText.isFocusable = false
            code4.textInputEditText.isFocusable = false
            code5.textInputEditText.isFocusable = false
            code6.textInputEditText.isFocusable = false
        }
    }

    override fun showErrorMessage(errorMessage: String) {

        val mySnackbar = Snackbar.make(_verificationBinding.container, errorMessage, 2000)

        mySnackbar.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.orange)))
        mySnackbar.show()
    }

    private fun changedResendTextColor(wasResendedCode: Boolean) {

        if (wasResendedCode) {

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

    private fun getUIState() {

        val codes = listOf(
            Pair(_verificationBinding.code1.textInputEditText, _verificationViewModel.code1),
            Pair(_verificationBinding.code2.textInputEditText, _verificationViewModel.code2),
            Pair(_verificationBinding.code3.textInputEditText, _verificationViewModel.code3),
            Pair(_verificationBinding.code4.textInputEditText, _verificationViewModel.code4),
            Pair(_verificationBinding.code5.textInputEditText, _verificationViewModel.code5),
            Pair(_verificationBinding.code6.textInputEditText, _verificationViewModel.code6)
        )

        codes.forEach { pair ->

            if (pair.second.isNotEmpty()) {

                pair.first.setText(pair.second)
            }
        }
    }


    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

        when (event) {

            Lifecycle.Event.ON_CREATE -> {

            }

            Lifecycle.Event.ON_START -> {

            }

            Lifecycle.Event.ON_RESUME -> {

                setObservables()
                getUIState()
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