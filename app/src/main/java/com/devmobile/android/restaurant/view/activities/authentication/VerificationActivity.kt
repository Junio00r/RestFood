package com.devmobile.android.restaurant.view.activities.authentication

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.devmobile.android.restaurant.databinding.ActivityVerificationCodeBinding

class VerificationActivity : AppCompatActivity() {
    private lateinit var _verificationBinding: ActivityVerificationCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _verificationBinding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(_verificationBinding.root)

        with(_verificationBinding) {

            // Init with focus
            code1.textInputEditText.requestFocus()

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

            code1.textInputEditText.doAfterTextChanged {
                focus(code1.textInputEditText)
            }

            code2.textInputEditText.doAfterTextChanged {
                focus(code2.textInputEditText)
            }

            code3.textInputEditText.doAfterTextChanged {
                focus(code3.textInputEditText)
            }

            code4.textInputEditText.doAfterTextChanged {
                focus(code4.textInputEditText)
            }

            code5.textInputEditText.doAfterTextChanged {
                focus(code5.textInputEditText)
            }

            code6.textInputEditText.doAfterTextChanged {
                // check if code is valid on ViewModel
            }
        }
    }

    private fun <T : EditText> focus(text: T?) {

        if (text != null) {
            if (text.length() == 1) {
                text.requestFocus()
            }
        }
    }
}