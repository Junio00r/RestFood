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
import com.devmobile.android.restaurant.usecase.CalledFromXML
import com.devmobile.android.restaurant.usecase.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.usecase.RequestState
import com.devmobile.android.restaurant.databinding.ActivityFormDataBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.FormRepository
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
            _formBinding.inputUserName.textInputLayout.hint = "Name *"
            _formBinding.inputUserLastName.textInputLayout.hint = "Lastname"
            _formBinding.inputUserEmail.textInputLayout.hint = "Email *"
            _formBinding.inputUserPassword.textInputLayout.hint = "Password *"

            // Setup InputType
            _formBinding.inputUserName.textInputEditText.inputType =
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            _formBinding.inputUserLastName.textInputEditText.inputType =
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            _formBinding.inputUserEmail.textInputEditText.inputType =
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            _formBinding.inputUserEmail.textInputEditText.inputType =
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            _formBinding.inputUserPassword.textInputLayout.endIconMode =
                TextInputLayout.END_ICON_PASSWORD_TOGGLE
            _formBinding.inputUserEmail.textInputEditText.maxLength(256)

            // Enable Counter
            _formBinding.inputUserEmail.textInputLayout.isCounterEnabled = true
        }
    }

    private fun subscribeObservers() {

        with(_formBinding) {

            // Errors observables
            _formViewModel.nameErrorPropagator.observe(this@FormActivity) { error ->

                inputUserName.textInputLayout.error = error
            }

            _formViewModel.lastNameErrorPropagator.observe(this@FormActivity) { error ->

                inputUserLastName.textInputLayout.error = error
            }

            _formViewModel.emailErrorPropagator.observe(this@FormActivity) { error ->

                inputUserEmail.textInputLayout.error = error
            }

            _formViewModel.passwordErrorPropagator.observe(this@FormActivity) { error ->

                inputUserPassword.textInputLayout.error = error
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
            inputUserName.textInputEditText.doAfterTextChanged {

                _formViewModel.onNameChanged(it.toString())
            }

            inputUserLastName.textInputEditText.doAfterTextChanged {

                _formViewModel.onLastNameChanged(it.toString())
            }

            inputUserEmail.textInputEditText.doAfterTextChanged {

                _formViewModel.onEmailChanged(it.toString())
            }

            inputUserPassword.textInputEditText.doAfterTextChanged {

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
                            _formBinding.inputUserName.textInputEditText.text.toString()
                        )
                        putExtra(
                            "EXTRA_LASTNAME",
                            _formBinding.inputUserLastName.textInputEditText.text.toString()
                        )
                        putExtra(
                            "EXTRA_EMAIL",
                            _formBinding.inputUserEmail.textInputEditText.text.toString()
                        )
                        putExtra(
                            "EXTRA_PASSWORD",
                            _formBinding.inputUserPassword.textInputEditText.text.toString()
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

    @CalledFromXML
    fun nextRegister() {

        _formViewModel.registerTrigger()
    }

    @CalledFromXML
    fun cancelRegister() {

        finish()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        with(_formBinding) {

            inputUserName.textInputEditText.append(_formViewModel.userName)
            inputUserLastName.textInputEditText.append(_formViewModel.userLastName)
            inputUserEmail.textInputEditText.append(_formViewModel.userEmail)
            inputUserPassword.textInputEditText.append(_formViewModel.userPassword)
        }
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