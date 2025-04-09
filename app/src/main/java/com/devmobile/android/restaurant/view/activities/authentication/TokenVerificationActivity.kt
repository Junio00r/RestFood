package com.devmobile.android.restaurant.view.activities.authentication

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devmobile.android.restaurant.usecase.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.usecase.RequestState
import com.devmobile.android.restaurant.databinding.ActivityVerificationCodeBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.TokenVerificationRepository
import com.devmobile.android.restaurant.model.datasource.remote.EmailApiService
import com.devmobile.android.restaurant.usecase.maxLength
import com.devmobile.android.restaurant.view.activities.bottomnavigation.BottomNavigationActivity
import com.devmobile.android.restaurant.view.customelements.TextInput
import com.devmobile.android.restaurant.viewmodel.authentication.TokenVerificationViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.io.IOException

class TokenVerificationActivity : AppCompatActivity(), IShowError {

    // references
    private lateinit var _viewBinding: ActivityVerificationCodeBinding
    private lateinit var _viewModel: TokenVerificationViewModel
    private val _repository = TokenVerificationRepository(
        localDatabase = RestaurantLocalDatabase.getInstance(this@TokenVerificationActivity),
        emailCommunicationHandler = EmailApiService()
    )

    // data
    private val _numbers = ArrayList<TextInput>()
    private lateinit var dataUser: Collection<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityVerificationCodeBinding.inflate(layoutInflater)
        setContentView(_viewBinding.root)

        // init data
        dataUser = arrayListOf(
            intent.extras!!.getString("EXTRA_NAME").toString(),
            intent.extras!!.getString("EXTRA_LASTNAME").toString(),
            intent.extras!!.getString("EXTRA_EMAIL").toString(),
            intent.extras!!.getString("EXTRA_PASSWORD").toString()
        )

        _numbers.addAll(
            arrayOf(
                _viewBinding.number1,
                _viewBinding.number2,
                _viewBinding.number3,
                _viewBinding.number4,
                _viewBinding.number5,
                _viewBinding.number6
            )
        )

        // about viewmodel
        val viewModelInstance: TokenVerificationViewModel by viewModels {
            TokenVerificationViewModel.provideFactory(
                repository = _repository,
                owner = this@TokenVerificationActivity,
                userData = dataUser,
                templateWithoutCodeDefined = getTemplate()
            )
        }

        _viewModel = viewModelInstance
        _viewBinding.viewModel = _viewModel


        setObservables()
        drawingViews()
        // restore focus
        restoreFocusOrSendCode()
    }

    private fun drawingViews() {

        _viewBinding.textInformation.append(" ${dataUser.elementAt(2)}")
        _numbers.forEach { number ->

            // InputType
            number.textInputEditText.inputType = InputType.TYPE_CLASS_NUMBER

            // Text Alignment
            number.textInputEditText.gravity = Gravity.CENTER

            // Max Chars in Text
            number.textInputEditText.maxLength(1)

            number.updateLayoutParams { height = 300 }
            number.textInputLayout.updateLayoutParams { height = 300 }

            number.textInputLayout.isErrorEnabled = true
            number.textInputLayout.errorIconDrawable = null
            number.textInputLayout.isHelperTextEnabled = false
        }

    }

    private fun setObservables() {

        _viewBinding.toolbarBack.setNavigationOnClickListener {

            finish()
        }

        // about inputs
        _viewModel.canStillEnterCodes.observe(this@TokenVerificationActivity) { mayEnableInput ->

            _numbers.forEach { it.isEnabled = mayEnableInput }
        }

        _viewModel.canResendCode.observe(this@TokenVerificationActivity) { canResendCode ->

            changedResendTextColor(canResendCode)

            if (!canResendCode) {

                clearInput(_numbers.map { it.textInputEditText })
                _numbers.forEach { it.textInputLayout.error = null }
                restoreFocusOrSendCode()
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                _viewModel.resultRequestCreateAcc.collect { requestResult ->

                    handleLoadState(requestResult)
                }
            }
        }

        _numbers[0].textInputEditText.doAfterTextChanged {

            _viewModel.saveNumber1(it.toString())
            restoreFocusOrSendCode()
        }
        _numbers[1].textInputEditText.doAfterTextChanged {

            _viewModel.saveNumber2(it.toString())
            restoreFocusOrSendCode()
        }
        _numbers[2].textInputEditText.doAfterTextChanged {

            _viewModel.saveNumber3(it.toString())
            restoreFocusOrSendCode()
        }
        _numbers[3].textInputEditText.doAfterTextChanged {

            _viewModel.saveNumber4(it.toString())
            restoreFocusOrSendCode()
        }
        _numbers[4].textInputEditText.doAfterTextChanged {

            _viewModel.saveNumber5(it.toString())
            restoreFocusOrSendCode()
        }
        _numbers[5].textInputEditText.doAfterTextChanged {

            _viewModel.saveNumber6(it.toString())
            restoreFocusOrSendCode()
        }
    }

    private fun restoreFocusOrSendCode() {

        _numbers.map { it.textInputLayout }
            .firstOrNull { it.editText?.text.isNullOrEmpty() }
            ?.requestFocus() ?: _viewModel.sendCodesInsertedTrigger()
    }

    private fun <T : EditText> clearInput(inputs: Collection<T>) {

        inputs.forEach {

            it.text.clear()
        }
    }

    private fun changedResendTextColor(wasCodeResent: Boolean) {

        when (wasCodeResent) {

            true -> {

                _viewBinding.textResendCode.setTextColor(

                    ColorStateList.valueOf(
                        getColor(
                            R.color.black_two
                        )
                    )
                )
            }

            false -> {

                _viewBinding.textResendCode.setTextColor(

                    ColorStateList.valueOf(
                        getColor(
                            R.color.gray_two
                        )
                    )
                )
            }
        }
    }

    private fun handleLoadState(requestOfResult: RequestResult?) {

        when (requestOfResult) {

            is RequestResult.Success -> {

                setResult(Activity.RESULT_OK)
                startActivity(Intent(this, BottomNavigationActivity::class.java))
                finish()
            }

            is RequestResult.Error -> {

                _numbers.forEach { it.textInputLayout.error = "Invalid Code" }
                showErrorMessage(
                    requestOfResult.exception.message ?: "Isn't possible create account"
                )
            }

            else -> {

                // Nothing
            }
        }
    }

    private fun getTemplate(): String {
        return try {

            this@TokenVerificationActivity.assets.use {
                it.open("verification_email_template.html").bufferedReader().readText()
            }
        } catch (e: IOException) {
            Log.e("File", "No template file founded")
            "No template file founded"
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        with(_viewBinding) {

            number1.textInputEditText.append(_viewModel.number1)
            number2.textInputEditText.append(_viewModel.number2)
            number3.textInputEditText.append(_viewModel.number3)
            number4.textInputEditText.append(_viewModel.number4)
            number5.textInputEditText.append(_viewModel.number5)
            number6.textInputEditText.append(_viewModel.number6)
        }
    }

    override fun showErrorMessage(errorMessage: String) {

        val mySnackBar = Snackbar.make(_viewBinding.container, errorMessage, 3500)

        mySnackBar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
        mySnackBar.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.red_light_one)))
        mySnackBar.setActionTextColor(ColorStateList.valueOf(this.getColor(R.color.white)))
        mySnackBar.setAction("OK") {
            mySnackBar.dismiss()
        }.show()
    }
}
