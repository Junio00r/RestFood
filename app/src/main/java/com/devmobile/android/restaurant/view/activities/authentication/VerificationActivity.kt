package com.devmobile.android.restaurant.view.activities.authentication

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.devmobile.android.restaurant.databinding.ActivityVerificationCodeBinding

class VerificationActivity : AppCompatActivity() {
    private lateinit var _verificationBinding: ActivityVerificationCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _verificationBinding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(_verificationBinding.root)

        _verificationBinding.code1.textInputEditText.requestFocus()

        _verificationBinding.code1.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
        _verificationBinding.code2.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
        _verificationBinding.code3.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
        _verificationBinding.code4.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
        _verificationBinding.code5.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER
        _verificationBinding.code6.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER

        _verificationBinding.code1.textInputEditText.gravity = Gravity.CENTER
        _verificationBinding.code2.textInputEditText.gravity = Gravity.CENTER
        _verificationBinding.code3.textInputEditText.gravity = Gravity.CENTER
        _verificationBinding.code4.textInputEditText.gravity = Gravity.CENTER
        _verificationBinding.code5.textInputEditText.gravity = Gravity.CENTER
        _verificationBinding.code6.textInputEditText.gravity = Gravity.CENTER

        _verificationBinding.code1.textInputEditText.maxLength(1)
        _verificationBinding.code2.textInputEditText.maxLength(1)
        _verificationBinding.code3.textInputEditText.maxLength(1)
        _verificationBinding.code4.textInputEditText.maxLength(1)
        _verificationBinding.code5.textInputEditText.maxLength(1)
        _verificationBinding.code6.textInputEditText.maxLength(1)

        setObservables()
    }

    private fun setObservables() {
        with(_verificationBinding) {

            code1.textInputEditText.doAfterTextChanged {
                if (it != null) {
                    if (it.length == 1)
                        code2.textInputEditText.requestFocus()
                }
            }

            code2.textInputEditText.doAfterTextChanged {
                if (it != null) {
                    if (it.length == 1)
                        code3.textInputEditText.requestFocus()
                }
            }

            code3.textInputEditText.doAfterTextChanged {
                if (it != null) {
                    if (it.length == 1)
                        code4.textInputEditText.requestFocus()
                }
            }

            code4.textInputEditText.doAfterTextChanged {
                if (it != null) {
                    if (it.length == 1)
                        code5.textInputEditText.requestFocus()
                }
            }

            code5.textInputEditText.doAfterTextChanged {
                if (it != null) {
                    if (it.length == 1)
                        code6.textInputEditText.requestFocus()
                }
            }

            code6.textInputEditText.doAfterTextChanged {
                // check if code is valid
            }
        }
    }
}