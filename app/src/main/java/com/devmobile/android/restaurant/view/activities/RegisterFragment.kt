package com.devmobile.android.restaurant.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.FragmentRegisterUserBinding
import com.devmobile.android.restaurant.databinding.LayoutTextInputBinding
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import com.devmobile.android.restaurant.view.customelements.LoadingTransition
import com.devmobile.android.restaurant.viewmodel.RegisterViewModel
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class RegisterFragment : AppCompatActivity(), IShowError {

    private lateinit var _registerBinding: FragmentRegisterUserBinding
    private var isVisible = true

    private val registerRepository = RegisterRepository(this)

    private val _registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(registerRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _registerBinding = DataBindingUtil.setContentView(this, R.layout.fragment_register_user)

        _registerBinding.lifecycleOwner = this
        _registerBinding.registerView = this

        // functions
        subscribeObservables()
        setTextInputParameters()
        getUIState()
        _registerBinding.invalidateAll()
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

            _registerViewModel.userNameError.observe(this@RegisterFragment) { error ->

                handleError(textUserName, error)
            }

            _registerViewModel.userEmailError.observe(this@RegisterFragment) { error ->

                handleError(textUserEmail, error)
            }

            _registerViewModel.userPasswordError.observe(this@RegisterFragment) { error ->

                handleError(textUserPassword, error)
            }

            _registerViewModel.loadingProgress.observe(this@RegisterFragment) { loadState ->

                handleLoadState(loadState)
            }

            // Listeners after text changed
            textUserName.textInputEditText.doAfterTextChanged {

                _registerViewModel.updateUIState(newName = it.toString())
            }

            textUserLastName.textInputEditText.doAfterTextChanged {
                _registerViewModel.updateUIState(newLastName = it.toString())
            }

            textUserEmail.textInputEditText.doAfterTextChanged {
                _registerViewModel.updateUIState(newEmail = it.toString())
            }

            textUserPassword.textInputEditText.doAfterTextChanged {
                _registerViewModel.updateUIState(newPassword = it.toString())
            }
        }
    }

    private fun handleError(inputText: LayoutTextInputBinding, error: String?) {

        if (error == RegisterViewModel.VALID_DATA) {

            inputText.textInputForm.error = null

        } else {

            inputText.textInputForm.error = error
        }
    }

    private fun handleLoadState(loadState: LoadState) {

        when (loadState) {

            is LoadState.Loading -> {

                LoadingTransition.getInstance(R.layout.layout_loading)
                    .start(supportFragmentManager, R.id.registerContainer)
            }

            is LoadState.NotLoading -> {

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        startActivity(Intent(this@RegisterFragment, MainActivity::class.java))
                        finish()
                    }, 3000
                )

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        LoadingTransition.getInstance(null).stop(supportFragmentManager)

                    }, 4000
                )
            }

            is LoadState.Error -> {

                LoadingTransition.getInstance(null).stop(supportFragmentManager)
                showErrorMessage(loadState.error.message ?: "Login Error")
            }
        }
    }

    private fun getUIState() {

        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            _registerViewModel.registerUIState.collect { uiState ->
                with(_registerBinding) {
                    Log.i(
                        "Fragment",
                        "${_registerBinding}:${_registerBinding.textUserName} Pegou UI"
                    )
                    Log.i("Fragment", "${uiState.name}")
                    textUserName.textInputEditText.setText(uiState.name)
                    textUserLastName.textInputEditText.setText(uiState.lastName)
                    textUserEmail.textInputEditText.setText(uiState.email)
                    textUserPassword.textInputEditText.setText(uiState.password)
                }
            }
        }.cancel()
    }

    private fun changeVisibilityState() {

        if (isVisible) {

            _registerBinding.apply {

//                textUserName.textInputForm.visibility = View.GONE
//                textUserLastName.textInputForm.visibility = View.GONE
//                textUserEmail.textInputForm.visibility = View.GONE
//                textUserPassword.textInputForm.visibility = View.GONE

                buttonCancelRegister.isClickable = false
                buttonConfirmRegister.isClickable = false
            }
            isVisible = false

        } else {

            _registerBinding.apply {

//                textUserName.textInputForm.visibility = View.VISIBLE
//                textUserLastName.textInputForm.visibility = View.VISIBLE
//                textUserEmail.textInputForm.visibility = View.VISIBLE
//                textUserPassword.textInputForm.visibility = View.VISIBLE

                buttonCancelRegister.isClickable = true
                buttonConfirmRegister.isClickable = true
            }

            isVisible = true
        }
    }

    @CalledFromXML
    fun startRequestRegister() {

        _registerViewModel.registerTrigger()
    }

    @CalledFromXML
    fun cancelRegister() {

        finish()
    }

    override fun showErrorMessage(errorMessage: String) {
        val mySnackBar = Snackbar.make(_registerBinding.registerContainer, errorMessage, 2000)

        mySnackBar.setAction("OK") {
            mySnackBar.dismiss()
        }.show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("UI State", "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("UI State", "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("UI State", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("UI State", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("UI State", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("UI State", "Lifecycle.State.DESTROYED onDestroy")
    }
}