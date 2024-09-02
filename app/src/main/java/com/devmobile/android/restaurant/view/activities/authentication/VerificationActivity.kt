package com.devmobile.android.restaurant.view.activities.authentication

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import com.devmobile.android.restaurant.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.ActivityVerificationCodeBinding
import com.devmobile.android.restaurant.extensions.maxLength
import com.devmobile.android.restaurant.model.repository.authentication.VerificationRepository
import com.devmobile.android.restaurant.view.customelements.TextInput
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.devmobile.android.restaurant.viewmodel.authentication.VerificationViewModel
import com.google.android.material.snackbar.Snackbar

class VerificationActivity : AppCompatActivity(), IShowError {

    // references
    private lateinit var _viewBinding: ActivityVerificationCodeBinding
    private val _repository = VerificationRepository(this)
    private val _viewModel: VerificationViewModel by viewModels {

        ViewModelFactory(
            repository = _repository, ownerOfStateToSave = this, defaultValuesForNulls = null
        )
    }

    // data
    private val _codes = ArrayList<TextInput>()

    // Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(_viewBinding.root)

        _viewBinding.viewModel = _viewModel
        _viewBinding.activity = this

        _codes.addAll(
            arrayOf(
                _viewBinding.code1,
                _viewBinding.code2,
                _viewBinding.code3,
                _viewBinding.code4,
                _viewBinding.code5,
                _viewBinding.code6
            )
        )

        setObservables()
        drawingViews()
    }

    private fun drawingViews() {

        _codes.forEach { code ->

            // InputType
            code.getTextInputEditText().inputType = InputType.TYPE_CLASS_NUMBER

            // Text Alignment
            code.getTextInputEditText().gravity = Gravity.CENTER

            // Max Chars in Text
            code.getTextInputEditText().maxLength(1)

            code.updateLayoutParams { height = 300 }
            code.getTextInput().updateLayoutParams { height = 300 }

            code.isErrorEnabled = false
        }

        restoreFocus()
    }

    private fun setObservables() {

        _viewModel.canStillEnterCodes.observe(this@VerificationActivity) { mayEnableInput ->

            enableInputs(mayEnableInput)
        }

        _viewModel.codeErrorPropagator.observe(this@VerificationActivity) { error ->

            if (error.isNullOrEmpty()) {

                showErrorMessage(error!!)
            }
        }

        _viewModel.canResendCode.observe(this@VerificationActivity) { canResendCode ->

            changedResendTextColor(canResendCode)

            if (!canResendCode) {

                clearInput(_codes.map { it.getTextInputEditText() })
            }
        }

        _viewBinding.toolbarBack.setNavigationOnClickListener {

            finish()
        }

        _codes[0].getTextInputEditText().doAfterTextChanged {

            _viewModel.saveCode1(it.toString())
            restoreFocus()
        }

        _codes[1].getTextInputEditText().doAfterTextChanged {

            _viewModel.saveCode2(it.toString())
            restoreFocus()
        }

        _codes[2].getTextInputEditText().doAfterTextChanged {

            _viewModel.saveCode3(it.toString())
            restoreFocus()
        }

        _codes[3].getTextInputEditText().doAfterTextChanged {

            _viewModel.saveCode4(it.toString())
            restoreFocus()
        }

        _codes[4].getTextInputEditText().doAfterTextChanged {

            _viewModel.saveCode5(it.toString())
            restoreFocus()
        }

        _codes[5].getTextInputEditText().doAfterTextChanged {

            _viewModel.saveCode6(it.toString())
            restoreFocus()
        }
    }

    private fun transferCodes() {

        _viewModel.requestCodesValidation(

            codesEntered = _codes.map { it.getTextInputEditText().text.toString() }
        )
    }

    private fun restoreFocus() {

        val nextInputEmpty = _codes.firstOrNull {

            it.getTextInputEditText().text.isNullOrEmpty()
        }?.requestFocus()

        if (nextInputEmpty == null) {
            transferCodes()
        }
    }

    private fun enableInputs(isFocusable: Boolean) {

        _codes.forEach {

            it.isEnabled = isFocusable
        }
    }

    private fun <T> clearInput(inputs: Collection<T>) where T : EditText {

        inputs.forEach {

            it.text.clear()
        }
    }

    private fun changedResendTextColor(wasResendCode: Boolean) {

        if (wasResendCode) {

            _viewBinding.textResendCode.setTextColor(
                ColorStateList.valueOf(
                    getColor(
                        R.color.black_two
                    )
                )
            )

        } else {

            _viewBinding.textResendCode.setTextColor(
                getColor(
                    R.color.gray_two
                )
            )
        }
    }

    override fun showErrorMessage(errorMessage: String) {

        val mySnackBar = Snackbar.make(_viewBinding.container, errorMessage, 2000)

        mySnackBar.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.orange)))
        mySnackBar.show()
    }
}