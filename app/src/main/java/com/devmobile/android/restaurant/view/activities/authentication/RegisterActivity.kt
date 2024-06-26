package com.devmobile.android.restaurant.view.activities.authentication

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
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
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import com.devmobile.android.restaurant.view.activities.MainActivity
import com.devmobile.android.restaurant.view.customelements.LoadingTransition
import com.devmobile.android.restaurant.viewmodel.RegisterViewModel
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity(), IShowError, LifecycleEventObserver {

    private lateinit var _registerBinding: ActivityRegisterUserBinding

    // I prefer to use the SavedStateHandle to practices
    private val _registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(
            repository = registerRepository,
            ownerOfStateToSave = this,
            defaultValuesForNulls = null
        )
    }

    private val registerRepository = RegisterRepository(this)

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
            _registerViewModel.nameErrorPropagator.observe(this@RegisterActivity) { error ->

                textUserName.textInputForm.error = error
            }

            _registerViewModel.lastNameErrorPropagator.observe(this@RegisterActivity) { error ->

                textUserLastName.textInputForm.error = error
            }

            _registerViewModel.emailErrorPropagator.observe(this@RegisterActivity) { error ->

                textUserEmail.textInputForm.error = error
            }

            _registerViewModel.passwordErrorPropagator.observe(this@RegisterActivity) { error ->

                textUserPassword.textInputForm.error = error
            }

            // LoadState
            _registerViewModel.loadingProgress.observe(this@RegisterActivity) { loadState ->

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

//                LoadingTransition.getInstance(R.layout.layout_loading)
//                    .start(supportFragmentManager, R.id.registerContainer)
                startActivity(Intent(this@RegisterActivity, VerificationActivity::class.java))
            }

            is LoadState.NotLoading -> {

//                Handler(Looper.getMainLooper()).postDelayed(
//                    {
//                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
//                        finish()
//                    }, 3000
//                )
//
//                Handler(Looper.getMainLooper()).postDelayed(
//                    {
//                        LoadingTransition.getInstance(null).stop(supportFragmentManager)
//
//                    }, 4000
//                )
            }

            is LoadState.Error -> {

//                LoadingTransition.getInstance(null).stop(supportFragmentManager)
                showErrorMessage(loadState.error.message ?: "Login Error")
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

            }

            Lifecycle.Event.ON_START -> {

            }

            Lifecycle.Event.ON_RESUME -> {

                subscribeObservables()
                setTextInputParameters()
                getUIState()
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