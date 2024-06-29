package com.devmobile.android.restaurant.view.activities.authentication

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.devmobile.android.restaurant.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.ActivityVerificationCodeBinding
import com.devmobile.android.restaurant.model.repository.remotedata.VerificationRepository
import com.devmobile.android.restaurant.viewmodel.VerificationViewModel
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class VerificationActivity : AppCompatActivity(), IShowError {
    private lateinit var _verificationBinding: ActivityVerificationCodeBinding

    private val _verificationRepository = VerificationRepository(this)

    private val _verificationViewModel: VerificationViewModel by viewModels {
        ViewModelFactory(
            repository = _verificationRepository,
            ownerOfStateToSave = this,
            defaultValuesForNulls = null
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _verificationBinding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(_verificationBinding.root)

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
        }

        setObservables()
    }

    private fun setObservables() {
        with(_verificationBinding) {

            // validate of codes
            _verificationViewModel.isCodeValid.observe(this@VerificationActivity) { isValid ->

                if (isValid) {
                    // make anything

                } else {

                    showErrorMessage("Os códigos são inválidos")
                }
            }

            // TextInput text
            code1.textInputEditText.doAfterTextChanged {
                code1.textInputEditText.requestFocus()
                focus(code2.textInputEditText)
            }

            code2.textInputEditText.doAfterTextChanged {
                focus(code3.textInputEditText)
            }

            code3.textInputEditText.doAfterTextChanged {
                focus(code4.textInputEditText)
            }

            code4.textInputEditText.doAfterTextChanged {
                focus(code5.textInputEditText)
            }

            code5.textInputEditText.doAfterTextChanged {
                focus(code6.textInputEditText)
            }

            code6.textInputEditText.doAfterTextChanged {
                sendCode()
                disableInputs()
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

            val code1 = code1.textInputEditText.text.toString()
            val code2 = code2.textInputEditText.text.toString()
            val code3 = code3.textInputEditText.text.toString()
            val code4 = code4.textInputEditText.text.toString()
            val code5 = code5.textInputEditText.text.toString()
            val code6 = code6.textInputEditText.text.toString()

            _verificationViewModel.codeVerification(
                code1, code2, code3, code4, code5, code6
            )
        }
    }

    private fun disableInputs() {
        with(_verificationBinding) {

            code1.textInputEditText.isFocusable = false
            code2.textInputEditText.isFocusable = false
            code3.textInputEditText.isFocusable = false
            code4.textInputEditText.isFocusable = false
            code5.textInputEditText.isFocusable = false
            code6.textInputEditText.isFocusable = false
        }
    }

    @SuppressLint("ShowToast")
    override fun showErrorMessage(errorMessage: String) {
        val mySnackbar = Snackbar.make(_verificationBinding.container, errorMessage, 2000)

        mySnackbar.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.orange)))
        mySnackbar.show()
    }
}