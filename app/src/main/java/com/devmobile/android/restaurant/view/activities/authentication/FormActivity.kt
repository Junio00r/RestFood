package com.devmobile.android.restaurant.view.activities.authentication

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.RequestResult
import com.devmobile.android.restaurant.databinding.ActivityFormDataBinding
import com.devmobile.android.restaurant.model.repository.authentication.FormRepository
import com.devmobile.android.restaurant.view.customelements.TextInput
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.devmobile.android.restaurant.viewmodel.authentication.FormViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class FormActivity : AppCompatActivity(), IShowError, LifecycleEventObserver {

    private lateinit var _formBinding: ActivityFormDataBinding

    // I prefer to use the SavedStateHandle to practices
    private val _formViewModel: FormViewModel by viewModels {
        ViewModelFactory(
            repository = formRepository,
            ownerOfStateToSave = this,
            defaultValuesForNulls = null
        )
    }
    private val formRepository = FormRepository(this)

    private val dataEditText = ArrayList<TextInput>()

    init {

        lifecycle.addObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        _formBinding = ActivityFormDataBinding.inflate(layoutInflater)
        setContentView(_formBinding.root)

        _formBinding.registerView = this

        dataEditText.addAll(
            listOf(
                _formBinding.inputUserName.textInputEditText,
                _formBinding.inputUserLastName.textInputEditText,
                _formBinding.inputUserEmail.textInputEditText,
                _formBinding.inputUserPassword.textInputEditText
            )
        )
    }

    private fun setUpInputEditText() {

        _formBinding.apply {

            inputUserName.textInputEditText.requestFocus()

            // Set Hints
            inputUserName.textInputForm.hint = "Name *"
            inputUserLastName.textInputForm.hint = "Lastname"
            inputUserEmail.textInputForm.hint = "Email *"
            inputUserPassword.textInputForm.hint = "Password *"

            // Setup InputType
            inputUserName.textInputEditText.inputType =
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            inputUserLastName.textInputEditText.inputType =
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            inputUserEmail.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            inputUserPassword.textInputEditText.inputType =
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            // Setup icon
            inputUserPassword.textInputForm.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE

            // Enable Counter
            inputUserPassword.textInputForm.isCounterEnabled = true

        }
    }

    private fun subscribeObservables() {

        with(_formBinding) {

            // Errors observables
            _formViewModel.nameErrorPropagator.observe(this@FormActivity) { error ->

                inputUserName.textInputForm.error = error
            }

            _formViewModel.lastNameErrorPropagator.observe(this@FormActivity) { error ->

                inputUserLastName.textInputForm.error = error
            }

            _formViewModel.emailErrorPropagator.observe(this@FormActivity) { error ->

                inputUserEmail.textInputForm.error = error
            }

            _formViewModel.passwordErrorPropagator.observe(this@FormActivity) { error ->

                inputUserPassword.textInputForm.error = error
            }

            lifecycleScope.launch {

                _formViewModel.resultRequestData.collect { resultOfRequest ->

                    handleLoadState(resultOfRequest)
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

    private fun getUIState() {

        with(_formBinding) {

            inputUserName.textInputEditText.setText(_formViewModel.userName)
            inputUserLastName.textInputEditText.setText(_formViewModel.userLastName)
            inputUserEmail.textInputEditText.setText(_formViewModel.userEmail)
            inputUserPassword.textInputEditText.setText(_formViewModel.userPassword)
            inputUserName.textInputEditText.updateCursorPosition()
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

        mySnackBar.setActionTextColor(ColorStateList.valueOf(this.getColor(R.color.green_light_one)))
        mySnackBar.setAction("OK") {
            mySnackBar.dismiss()
        }.show()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

        when (event) {

            Lifecycle.Event.ON_CREATE -> {
                Log.i("Form", "ON_CREATE")
            }

            Lifecycle.Event.ON_START -> {

                Log.i("Form", "ON_START")
            }

            Lifecycle.Event.ON_RESUME -> {

                setUpInputEditText()
                subscribeObservables()
                getUIState()
                Log.i("Form", "ON_RESUME")
            }

            Lifecycle.Event.ON_PAUSE -> {
                Log.i("Form", "ON_PAUSE")
            }

            Lifecycle.Event.ON_STOP -> {
                Log.i("Form", "ON_STOP")
            }

            Lifecycle.Event.ON_DESTROY -> {
                Log.i("Form", "ON_DESTROY")
            }

            Lifecycle.Event.ON_ANY -> {
                Log.i("Form", "ON_ANY")
            }
        }
    }
}