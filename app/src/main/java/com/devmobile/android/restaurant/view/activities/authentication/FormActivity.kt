package com.devmobile.android.restaurant.view.activities.authentication

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.paging.LoadState
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.ActivityRegisterUserBinding
import com.devmobile.android.restaurant.model.repository.remotedata.FormRepository
import com.devmobile.android.restaurant.viewmodel.FormViewModel
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

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

    init {

        lifecycle.addObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_user)

        _registerBinding.lifecycleOwner = this
        _registerBinding.registerView = this
    }

    private fun setTextInputParameters() {

        _registerBinding.apply {

            textUserName.textInputEditText.requestFocus()

            // Set Hints
            textUserName.textInputForm.hint = "Name *"
            textUserLastName.textInputForm.hint = "Lastname"
            textUserEmail.textInputForm.hint = "Email *"
            textUserPassword.textInputForm.hint = "Password *"

            // Set InputType
            textUserName.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            textUserLastName.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            textUserEmail.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            textUserPassword.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

            // Set icons
            textUserPassword.textInputForm.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            textUserPassword.textInputForm.isEndIconVisible = true

            // Enable Counter
            textUserPassword.textInputForm.isCounterEnabled = true
        }
    }

    private fun subscribeObservables() {

        with(_registerBinding) {

            // Errors observables
            _registerViewModel.nameErrorPropagator.observe(this@FormActivity) { error ->

                textUserName.textInputForm.error = error
            }

            _registerViewModel.lastNameErrorPropagator.observe(this@FormActivity) { error ->

                textUserLastName.textInputForm.error = error
            }

            _registerViewModel.emailErrorPropagator.observe(this@FormActivity) { error ->

                textUserEmail.textInputForm.error = error
            }

            _registerViewModel.passwordErrorPropagator.observe(this@FormActivity) { error ->

                textUserPassword.textInputForm.error = error
            }

            // LoadState
            _registerViewModel.loadingProgress.observe(this@FormActivity) { loadState ->

                handleLoadState(loadState)
            }

            // Listeners after text changed
            textUserName.textInputEditText.doAfterTextChanged {
                _registerViewModel.onNameChanged(it.toString())
            }

            textUserLastName.textInputEditText.doAfterTextChanged {
                _registerViewModel.onLastNameChanged(it.toString())
            }

            textUserEmail.textInputEditText.doAfterTextChanged {
                _registerViewModel.onEmailChanged(it.toString())
            }

            textUserPassword.textInputEditText.doAfterTextChanged {
                _registerViewModel.onPasswordChanged(it.toString())
            }
        }
    }

    private fun handleLoadState(loadState: LoadState) {

        when (loadState) {

            is LoadState.Loading -> {

                startActivity(Intent(this@FormActivity, VerificationActivity::class.java))
                finish()
            }

            is LoadState.NotLoading -> {
                TODO()
            }

            is LoadState.Error -> {

                showErrorMessage(loadState.error.message ?: "Register Error")
            }
        }
    }

    private fun getUIState() {

        with(_registerBinding) {

            textUserName.textInputEditText.append(_registerViewModel.userName)
            textUserLastName.textInputEditText.append(_registerViewModel.userLastName)
            textUserEmail.textInputEditText.append(_registerViewModel.userEmail)
            textUserPassword.textInputEditText.append(_registerViewModel.userPassword)
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
            }

            Lifecycle.Event.ON_START -> {
                Log.i("Form", "ON_START")
            }

            Lifecycle.Event.ON_RESUME -> {

                subscribeObservables()
                setTextInputParameters()
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