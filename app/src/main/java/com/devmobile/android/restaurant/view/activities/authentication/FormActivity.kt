package com.devmobile.android.restaurant.view.activities.authentication

import android.content.Intent
import android.content.res.ColorStateList
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
import com.devmobile.android.restaurant.databinding.ActivityRegisterUserBinding
import com.devmobile.android.restaurant.model.repository.authentication.FormRepository
import com.devmobile.android.restaurant.view.customelements.TextInput
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.devmobile.android.restaurant.viewmodel.authentication.FormViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class FormActivity : AppCompatActivity(), IShowError, LifecycleEventObserver {

    private lateinit var _registerBinding: ActivityRegisterUserBinding

    // I prefer to use the SavedStateHandle to practices
    private val _registerViewModel: FormViewModel by viewModels {
        ViewModelFactory(
            repository = registerRepository,
            ownerOfStateToSave = this,
            defaultValuesForNulls = null
        )
    }

    private val registerRepository = FormRepository(this)

    private val dataEditText = ArrayList<TextInput>()

    init {

        lifecycle.addObserver(this)
    }

    private fun initMembers() {

        _registerBinding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(_registerBinding.root)

        _registerBinding.registerView = this

        dataEditText.addAll(
            listOf(
                _registerBinding.inputUserName.textInputEditText,
                _registerBinding.inputUserLastName.textInputEditText,
                _registerBinding.inputUserEmail.textInputEditText,
                _registerBinding.inputUserPassword.textInputEditText
            )
        )
    }

    private fun setTextInputParameters() {

        _registerBinding.apply {

            inputUserName.textInputEditText.requestFocus()

            // Set Hints
            inputUserName.textInputForm.hint = "Name *"
            inputUserLastName.textInputForm.hint = "Lastname"
            inputUserEmail.textInputForm.hint = "Email *"
            inputUserPassword.textInputForm.hint = "Password *"

            // Set InputType
            inputUserName.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            inputUserLastName.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            inputUserEmail.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            inputUserPassword.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

            // Set icons
            inputUserPassword.textInputForm.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            inputUserPassword.textInputForm.isEndIconVisible = true

            // Enable Counter
            inputUserPassword.textInputForm.isCounterEnabled = true
        }
    }

    private fun subscribeObservables() {

        with(_registerBinding) {

            // Errors observables
            _registerViewModel.nameErrorPropagator.observe(this@FormActivity) { error ->

                inputUserName.textInputForm.error = error
            }

            _registerViewModel.lastNameErrorPropagator.observe(this@FormActivity) { error ->

                inputUserLastName.textInputForm.error = error
            }

            _registerViewModel.emailErrorPropagator.observe(this@FormActivity) { error ->

                inputUserEmail.textInputForm.error = error
            }

            _registerViewModel.passwordErrorPropagator.observe(this@FormActivity) { error ->

                inputUserPassword.textInputForm.error = error
            }

            lifecycleScope.launch {

                _registerViewModel.resultRequestData.collect { resultOfRequest ->

                    handleLoadState(resultOfRequest)
                }
            }

            // Listeners after text changed
            inputUserName.textInputEditText.doAfterTextChanged {
                _registerViewModel.onNameChanged(it.toString())
            }

            inputUserLastName.textInputEditText.doAfterTextChanged {
                _registerViewModel.onLastNameChanged(it.toString())
            }

            inputUserEmail.textInputEditText.doAfterTextChanged {
                _registerViewModel.onEmailChanged(it.toString())
            }

            inputUserPassword.textInputEditText.doAfterTextChanged {
                _registerViewModel.onPasswordChanged(it.toString())
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

        with(_registerBinding) {

            inputUserName.textInputEditText.setText(_registerViewModel.userName)
            inputUserLastName.textInputEditText.setText(_registerViewModel.userLastName)
            inputUserEmail.textInputEditText.setText(_registerViewModel.userEmail)
            inputUserPassword.textInputEditText.setText(_registerViewModel.userPassword)

            inputUserName.textInputEditText.requestFocus()
        }
    }

    @CalledFromXML
    fun nextRegister() {

        _registerViewModel.registerTrigger()
    }

    @CalledFromXML
    fun cancelRegister() {

        finish()
    }

    override fun showErrorMessage(errorMessage: String) {

        val mySnackBar = Snackbar.make(_registerBinding.registerContainer, errorMessage, 2000)

        mySnackBar.setActionTextColor(ColorStateList.valueOf(this.getColor(R.color.green_light_one)))
        mySnackBar.setAction("OK") {
            mySnackBar.dismiss()
        }.show()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

        when (event) {

            Lifecycle.Event.ON_CREATE -> {
                Log.i("Form", "ON_CREATE")
                initMembers()
                subscribeObservables()
                setTextInputParameters()
            }

            Lifecycle.Event.ON_START -> {
                Log.i("Form", "ON_START")
            }

            Lifecycle.Event.ON_RESUME -> {
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