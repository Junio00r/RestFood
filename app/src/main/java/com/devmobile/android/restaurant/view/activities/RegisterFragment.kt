package com.devmobile.android.restaurant.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.FragmentRegisterUserBinding
import com.devmobile.android.restaurant.databinding.LayoutTextInputBinding
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import com.devmobile.android.restaurant.view.customelements.LoadingTransition
import com.devmobile.android.restaurant.viewmodel.RegisterViewModel
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : AppCompatActivity() {
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
        registerBinding = DataBindingUtil.setContentView(this, R.layout.fragment_register_user)
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
        registerBinding.textUserName.textinputForm.hint = "Username"
        registerBinding.textUserLastName.textinputForm.hint = "Lastname"
        registerBinding.textUserEmail.textinputForm.hint = "UserEmail"
        registerBinding.textUserPassword.textinputForm.hint = "Password"

        // Set InputType
        textUserName.textinputForm.editText!!.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        textUserLastName.textinputForm.editText!!.inputType =
            InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        textUserEmail.textinputForm.editText!!.inputType =
            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        textUserPassword.textinputForm.editText!!.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD

        // Other paramters
        textUserPassword.textinputForm.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE

        textUserPassword.textinputForm.isEndIconVisible = true
        textUserPassword.textinputForm.isCounterEnabled = true
    }

    private fun subscribeObservables() {

        registerViewModel.userNameError.observe(this) { error ->

            if (error == RegisterViewModel.VALID_DATA) {

                textUserName.textinputForm.error = null
            } else {
                textUserName.textinputForm.error = error
            }
        }

        registerViewModel.userEmailError.observe(this) { error ->

            if (error == RegisterViewModel.VALID_DATA) {

                textUserEmail.textinputForm.error = null
            } else {
                textUserEmail.textinputForm.error = error
            }
        }

        registerViewModel.userPasswordError.observe(this) { error ->

            if (error == RegisterViewModel.VALID_DATA) {

                textUserPassword.textinputForm.error = null
            } else {
                textUserPassword.textinputForm.error = error
            }
        }

        registerViewModel.loadingProgress.observe(this) { loadState ->

            when (loadState) {

                is LoadState.Loading -> {

                    LoadingTransition
                        .getInstance(R.layout.layout_loading)
                        .start(supportFragmentManager, R.id.registerContainer, null)

                    Log.i("Teste: RegisterFragment", "Start Loading Screen")
                }

                is LoadState.NotLoading -> {

                    Log.i("Teste: RegisterFragment", "Stop Loading Screen")

                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                        LoadingTransition.getInstance(null).stop()
                    }, 5000)
                }

                is LoadState.Error -> {

                    LoadingTransition.getInstance(null).stop()
                    showError(loadState.error.message.toString())

                    Log.e("Teste: RegisterFragment", "Interrupt Loading Screen")
                }
            }
        }
    }

    @CalledFromXML
    fun register() {

        val userName = textUserName.textinputForm.editText?.text.toString()
        val userLastName = textUserLastName.textinputForm.editText?.text.toString()
        val userEmail = textUserEmail.textinputForm.editText?.text.toString()
        val userPassword = textUserPassword.textinputForm.editText?.text.toString()

        registerViewModel.register(userName, userLastName, userEmail, userPassword)
    }

    @CalledFromXML
    fun cancelRegister() {

        finish()
    }

    private fun showError(errorMessage: String) {

    }
}