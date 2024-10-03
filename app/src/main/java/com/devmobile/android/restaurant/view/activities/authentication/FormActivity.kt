package com.devmobile.android.restaurant.view.activities.authentication

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.RequestResult
import com.devmobile.android.restaurant.databinding.ActivityFormDataBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.authentication.FormRepository
import com.devmobile.android.restaurant.usecase.maxLength
import com.devmobile.android.restaurant.viewmodel.authentication.FormViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class FormActivity : AppCompatActivity(), IShowError {

    private lateinit var _formBinding: ActivityFormDataBinding
    private val formRepository = FormRepository(localDatabase = RestaurantLocalDatabase.getInstance(this@FormActivity))
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {
                result.data
                finish()
            }
        }

    // I prefer to use the SavedStateHandle to practices
    private val _formViewModel: FormViewModel by viewModels {
        FormViewModel.provideFactory(
            repository = formRepository,
            owner = this@FormActivity,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _formBinding = ActivityFormDataBinding.inflate(layoutInflater)
        setContentView(_formBinding.root)

        _formBinding.registerView = this@FormActivity

        setUpInputEditText()
        subscribeObservers()
    }

    private fun setUpInputEditText() {

        _formBinding.apply {

            // Set Hints
            _formBinding.inputUserName.getTextInput().hint = "Name *"
            _formBinding.inputUserLastName.getTextInput().hint = "Lastname"
            _formBinding.inputUserEmail.getTextInput().hint = "Email *"
            _formBinding.inputUserPassword.getTextInput().hint = "Password *"

            // Setup InputType
            _formBinding.inputUserName.getTextInputEditText().inputType =
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            _formBinding.inputUserLastName.getTextInputEditText().inputType =
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            _formBinding.inputUserEmail.getTextInputEditText().inputType =
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            _formBinding.inputUserEmail.getTextInputEditText().inputType =
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            _formBinding.inputUserPassword.getTextInput().endIconMode =
                TextInputLayout.END_ICON_PASSWORD_TOGGLE
            _formBinding.inputUserEmail.getTextInputEditText().maxLength(256)

            // Enable Counter
            _formBinding.inputUserEmail.getTextInput().isCounterEnabled = true
        }
    }

    private fun subscribeObservers() {

        with(_formBinding) {

            // Errors observables
            _formViewModel.nameErrorPropagator.observe(this@FormActivity) { error ->

                inputUserName.getTextInput().error = error
            }

            _formViewModel.lastNameErrorPropagator.observe(this@FormActivity) { error ->

                inputUserLastName.getTextInput().error = error
            }

            _formViewModel.emailErrorPropagator.observe(this@FormActivity) { error ->

                inputUserEmail.getTextInput().error = error
            }

            _formViewModel.passwordErrorPropagator.observe(this@FormActivity) { error ->

                inputUserPassword.getTextInput().error = error
            }

            lifecycleScope.launch {
                // API repeatOnLifecycle() to stop collect when activity is STOPPED
                repeatOnLifecycle(Lifecycle.State.STARTED) {

                    _formViewModel.resultRequestData.collect { resultOfRequest ->

                        handleLoadState(resultOfRequest)
                    }
                }
            }

            // Listeners after text changed
            inputUserName.getTextInputEditText().doAfterTextChanged {

                _formViewModel.onNameChanged(it.toString())
            }

            inputUserLastName.getTextInputEditText().doAfterTextChanged {

                _formViewModel.onLastNameChanged(it.toString())
            }

            inputUserEmail.getTextInputEditText().doAfterTextChanged {

                _formViewModel.onEmailChanged(it.toString())
            }

            inputUserPassword.getTextInputEditText().doAfterTextChanged {

                _formViewModel.onPasswordChanged(it.toString())
            }
        }
    }

    private fun handleLoadState(requestOfResult: RequestResult?) {

        when (requestOfResult) {


            is RequestResult.Success -> {

                val intent =
                    Intent(this@FormActivity, TokenVerificationActivity::class.java).apply {

                        putExtra(
                            "EXTRA_NAME",
                            _formBinding.inputUserName.getTextInputEditText().text.toString()
                        )
                        putExtra(
                            "EXTRA_LAST_NAME",
                            _formBinding.inputUserLastName.getTextInputEditText().text.toString()
                        )
                        putExtra(
                            "EXTRA_EMAIL",
                            _formBinding.inputUserEmail.getTextInputEditText().text.toString()
                        )
                        putExtra(
                            "EXTRA_PASSWORD",
                            _formBinding.inputUserPassword.getTextInputEditText().text.toString()
                        )
                    }

                startForResult.launch(intent)
            }

            is RequestResult.Error -> {

                showErrorMessage(requestOfResult.exception.message ?: "Register Error")
            }

            else -> {

                // Nothing
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        with(_formBinding) {

            inputUserName.getTextInputEditText().append(_formViewModel.userName)
            inputUserLastName.getTextInputEditText().append(_formViewModel.userLastName)
            inputUserEmail.getTextInputEditText().append(_formViewModel.userEmail)
            inputUserPassword.getTextInputEditText().append(_formViewModel.userPassword)
        }
    }

    @CalledFromXML
    fun nextRegister() {

        _formViewModel.registerTrigger()
    }

    @CalledFromXML
    fun cancelRegister() {

        finish()
    }

    override fun showErrorMessage(errorMessage: String) {

        val mySnackBar = Snackbar.make(_formBinding.registerContainer, errorMessage, 3500)

        mySnackBar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
        mySnackBar.setBackgroundTintList(ColorStateList.valueOf(this.getColor(R.color.red_light_one)))
        mySnackBar.setActionTextColor(ColorStateList.valueOf(this.getColor(R.color.white)))
        mySnackBar.setAction("OK") {
            mySnackBar.dismiss()
        }.show()
    }
}