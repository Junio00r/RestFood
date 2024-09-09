package com.devmobile.android.restaurant.view.activities.authentication

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.RequestResult
import com.devmobile.android.restaurant.databinding.ActivityFormDataBinding
import com.devmobile.android.restaurant.extensions.maxLength
import com.devmobile.android.restaurant.model.repository.authentication.FormRepository
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.devmobile.android.restaurant.viewmodel.authentication.FormViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class FormActivity : AppCompatActivity(), IShowError {

    private lateinit var _formBinding: ActivityFormDataBinding
    private val formRepository = FormRepository(this@FormActivity)
    // I prefer to use the SavedStateHandle to practices
    private val _formViewModel: FormViewModel by viewModels {
        ViewModelFactory(
            repository = formRepository,
            ownerOfStateToSave = this@FormActivity,
            defaultValuesForNulls = null
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _formBinding = ActivityFormDataBinding.inflate(layoutInflater)
        setContentView(_formBinding.root)

        _formBinding.registerView = this@FormActivity

        setUpInputEditText()
        subscribeObservables()
    }

    private fun setUpInputEditText() {

        _formBinding.apply {

            // Set Hints
            _formBinding.inputUserName.getTextInput().hint = "Name *"
            _formBinding.inputUserLastName.getTextInput().hint = "Lastname"
            _formBinding.inputUserEmail.getTextInput().hint = "Email *"
            _formBinding.inputUserPassword.getTextInput().hint = "Password *"

            // Setup InputType
            _formBinding.inputUserName.getTextInputEditText().inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            _formBinding.inputUserLastName.getTextInputEditText().inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            _formBinding.inputUserEmail.getTextInputEditText().inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            _formBinding.inputUserEmail.getTextInputEditText().inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            _formBinding.inputUserPassword.getTextInput().endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            _formBinding.inputUserEmail.getTextInputEditText().maxLength(256)

            // Enable Counter
            _formBinding.inputUserEmail.getTextInput().isCounterEnabled = true
        }
    }

    private fun subscribeObservables() {

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

            // Flow because LiveData send latest value after change configuration, but i not want it happens
            lifecycleScope.launch {

                _formViewModel.resultRequestData.collect { resultOfRequest ->

                    handleLoadState(resultOfRequest)
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

                startActivity(Intent(this@FormActivity, VerificationActivity::class.java))
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

    override fun showErrorMessage(errorMessage: String) {

        val mySnackBar = Snackbar.make(_formBinding.registerContainer, errorMessage, 2000)

        mySnackBar.setActionTextColor(ColorStateList.valueOf(this.getColor(R.color.white)))
        mySnackBar.setAction("OK") {
            mySnackBar.dismiss()
        }.show()
    }
}