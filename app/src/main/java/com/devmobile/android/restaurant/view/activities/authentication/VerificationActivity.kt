package com.devmobile.android.restaurant.view.activities.authentication

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.util.Log
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
import com.devmobile.android.restaurant.extensions.maxLength
import com.devmobile.android.restaurant.model.repository.authentication.VerificationRepository
import com.devmobile.android.restaurant.view.customelements.TextInput
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.devmobile.android.restaurant.viewmodel.authentication.VerificationViewModel
import com.google.android.material.snackbar.Snackbar

class VerificationActivity : AppCompatActivity(), IShowError, LifecycleEventObserver {

    // references
    private lateinit var _viewBinding: ActivityVerificationCodeBinding
    private val _repository = VerificationRepository(this)
    private val _viewModel: VerificationViewModel by viewModels {

        ViewModelFactory(
            repository = _repository, ownerOfStateToSave = this, defaultValuesForNulls = null
        )
    }

    // data
    private val codes = ArrayList<TextInput>()

    init {

        lifecycle.addObserver(this)
    }

    // Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(_viewBinding.root)

        _viewBinding.viewModel = _viewModel
        _viewBinding.activity = this

        codes.addAll(
            arrayOf(
//                _viewBinding.code1.textInputEditText,
//                _viewBinding.code2.textInputEditText,
//                _viewBinding.code3.textInputEditText,
//                _viewBinding.code4.textInputEditText,
//                _viewBinding.code5.textInputEditText,
//                _viewBinding.code6.textInputEditText
            )
        )
    }

    private fun drawingViews() {

        codes.forEach { code ->

            // InputType
//            code.inputType = InputType.TYPE_CLASS_NUMBER

            // Text Alignment
//            code.gravity = Gravity.CENTER

            // Max Chars in Text
//            code.maxLength(1)
        }

        with(_viewBinding) {

            // Layout text size
//            code1.textInputEditText.layoutParams.height = 300
//            code2.textInputEditText.layoutParams.height = 300
//            code3.textInputEditText.layoutParams.height = 300
//            code4.textInputEditText.layoutParams.height = 300
//            code5.textInputEditText.layoutParams.height = 300
//            code6.textInputEditText.layoutParams.height = 300

            setFocus()
        }
    }

    private fun setObservables() {

        _viewModel.isEnableInput.observe(this@VerificationActivity) { mayEnableInput ->

            mayFocusable(mayEnableInput)
            setFocus()
        }

        _viewModel.codeErrorPropagator.observe(this@VerificationActivity) { error ->

            if (error.isNullOrEmpty()) {

                showErrorMessage(error!!)
            }
        }

        _viewModel.canResendCode.observe(this@VerificationActivity) { canResendCode ->

            changedResendTextColor(canResendCode)

            if (!canResendCode) {

//                clearInput(codes)
            }
        }

        _viewBinding.toolbarBack.setNavigationOnClickListener {

            navigationToBack()
        }

//        codes[0].editText?.doAfterTextChanged {
//
//            _viewModel.saveCode1(it.toString())
//            sendCode()
//            setFocus()
//        }
//
//        codes[1].editText?.doAfterTextChanged {
//
//            _viewModel.saveCode2(it.toString())
//            sendCode()
//            setFocus()
//        }
//
//        codes[2].editText?.doAfterTextChanged {
//
//            _viewModel.saveCode3(it.toString())
//            sendCode()
//            setFocus()
//        }
//
//        codes[3].editText?.doAfterTextChanged {
//
//            _viewModel.saveCode4(it.toString())
//            sendCode()
//            setFocus()
//        }
//
//        codes[4].editText?.doAfterTextChanged {
//
//            _viewModel.saveCode5(it.toString())
//            sendCode()
//            setFocus()
//        }
//
//        codes[5].editText?.doAfterTextChanged {
//
//            _viewModel.saveCode6(it.toString())
//            setFocus()
//            sendCode()
//        }
    }

    private fun sendCode() {

        _viewModel.codesForVerify(
            codesEntered = arrayOf(
//                codes[0].editText?.text.toString(),
//                codes[1].editText?.text.toString(),
//                codes[2].editText?.text.toString(),
//                codes[3].editText?.text.toString(),
//                codes[4].editText?.text.toString(),
//                codes[5].editText?.text.toString(),
            )
        )
    }

    private fun navigationToBack() {
        finish()
    }

    private fun setFocus() {

//        codes.firstOrNull {
//
//            it.editText?.text.isNullOrEmpty()
//        }?.requestFocus()
    }

    private fun mayFocusable(isFocusable: Boolean) {

        codes.forEach {

            it.isFocusable = isFocusable
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

    private fun rememberUIState() {

        val codesForRememberWithValue = listOf(
            Pair(codes[0], _viewModel.code1),
            Pair(codes[1], _viewModel.code2),
            Pair(codes[2], _viewModel.code3),
            Pair(codes[3], _viewModel.code4),
            Pair(codes[4], _viewModel.code5),
            Pair(codes[5], _viewModel.code6)
        )

//        codesForRememberWithValue.forEach { pair ->
//
//            if (pair.second.isNotEmpty()) {
//
//                pair.first.editText?.setText(pair.second)
//            }
//        }
    }

//    private fun getInputs() : Array<out EditText> {
//        return arrayOf(
//            _viewBinding.code1.textInputEditText,
//            _viewBinding.code2.textInputEditText,
//            _viewBinding.code3.textInputEditText,
//            _viewBinding.code4.textInputEditText,
//            _viewBinding.code5.textInputEditText,
//            _viewBinding.code6.textInputEditText
//        )
//    }

    override fun onStop() {
        Log.i("Verification", "onStoooooop --------------------------------------")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i("Verification", "onDestroy ------------------------------------------")
        super.onDestroy()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

        when (event) {

            Lifecycle.Event.ON_CREATE -> {

            }

            Lifecycle.Event.ON_START -> {

            }

            Lifecycle.Event.ON_RESUME -> {
                setObservables()
                drawingViews()
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