package com.devmobile.android.restaurant.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.devmobile.android.restaurant.ICalledFromXML
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
    private lateinit var registerBinding: FragmentRegisterUserBinding

    private lateinit var textUserName: LayoutTextInputBinding
    private lateinit var textUserLastName: LayoutTextInputBinding
    private lateinit var textUserEmail: LayoutTextInputBinding
    private lateinit var textUserPassword: LayoutTextInputBinding

    private val registerRepository = RegisterRepository(this)
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(registerRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init layout
        registerBinding = FragmentRegisterUserBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        registerBinding.registerView = this

        // initialize variables
        textUserName = registerBinding.textUserName
        textUserLastName = registerBinding.textUserLastName
        textUserEmail = registerBinding.textUserEmail
        textUserPassword = registerBinding.textUserPassword

        // methods
        subscribeObservables()
        setParameters()
    }

    private fun setParameters() {
        // Set Hints
        registerBinding.textUserName.textInputForm.hint = "Username *"
        registerBinding.textUserLastName.textInputForm.hint = "Lastname"
        registerBinding.textUserEmail.textInputForm.hint = "UserEmail *"
        registerBinding.textUserPassword.textInputForm.hint = "Password *"

//        // Set InputType
        textUserName.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        textUserLastName.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        textUserEmail.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        textUserPassword.textInputEditText.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        // Other paramters
        textUserPassword.textInputForm.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE

        textUserPassword.textInputForm.isEndIconVisible = true
        textUserPassword.textInputForm.isCounterEnabled = true
    }

    private fun subscribeObservables() {

        registerViewModel.userNameError.observe(this) { error ->

            if (error == RegisterViewModel.VALID_DATA) {

                textUserName.textInputForm.error = null
            } else {
                textUserName.textInputForm.error = error
            }
        }

        registerViewModel.userEmailError.observe(this) { error ->

            if (error == RegisterViewModel.VALID_DATA) {

                textUserEmail.textInputForm.error = null
            } else {
                textUserEmail.textInputForm.error = error
            }
        }

        registerViewModel.userPasswordError.observe(this) { error ->

            if (error == RegisterViewModel.VALID_DATA) {

                textUserPassword.textInputForm.error = null
            } else {
                textUserPassword.textInputForm.error = error
            }
        }

        registerViewModel.loadingProgress.observe(this) { loadState ->

            when (loadState) {

                is LoadState.Loading -> {

                    LoadingTransition.getInstance(R.layout.layout_loading)
                        .start(supportFragmentManager, R.id.registerContainer, null)
                }

                is LoadState.NotLoading -> {

                    Log.i("Test", "Passou aqui")
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }, 3000
                    )

                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            LoadingTransition.getInstance(null).stop()

                        }, 6000
                    )
                }

                is LoadState.Error -> {

                    LoadingTransition.getInstance(null).stop()

                    showErrorMessage(loadState.error.message ?: "Error")
                }
            }
        }
    }

    @ICalledFromXML
    fun startRequestRegister() {

        val userName = textUserName.textInputForm.editText?.text.toString()
        val userLastName = textUserLastName.textInputForm.editText?.text.toString()
        val userEmail = textUserEmail.textInputForm.editText?.text.toString()
        val userPassword = textUserPassword.textInputForm.editText?.text.toString()

        lifecycleScope.launch {

            registerViewModel.registerTrigger(userName, userLastName, userEmail, userPassword)
        }
    }

    @ICalledFromXML
    fun cancelRegister() {

        finish()
    }

    private fun opacityViews() {
        textUserName.textInputForm.visibility = View.GONE
        textUserLastName.textInputForm.visibility = View.GONE
        textUserEmail.textInputForm.visibility = View.GONE
        textUserPassword.textInputForm.visibility = View.GONE
    }

    override fun showErrorMessage(errorMessage: String) {
        val mySnackBar = Snackbar.make(registerBinding.registerContainer, errorMessage, 2000)

        mySnackBar.setAction("OK") {
            mySnackBar.dismiss()
        }.show()
    }
}