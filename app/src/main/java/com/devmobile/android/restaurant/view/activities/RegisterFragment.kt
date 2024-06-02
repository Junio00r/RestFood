package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.paging.LoadState
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.FragmentRegisterUserBinding
import com.devmobile.android.restaurant.databinding.LayoutTextInputBinding
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import com.devmobile.android.restaurant.viewmodel.RegisterViewModel
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : FragmentActivity() {
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

            if (error != RegisterViewModel.VALID_DATA) {

                textUserName.textinputForm.error = error
            } else {

                textUserName.textinputForm.error = null
            }
        }

        registerViewModel.userEmailError.observe(this) { error ->

            if (error != RegisterViewModel.VALID_DATA) {

                textUserEmail.textinputForm.error = error
            } else {

                textUserEmail.textinputForm.error = null
            }
        }

        registerViewModel.userPasswordError.observe(this) { error ->

            if (error != RegisterViewModel.VALID_DATA) {

                textUserPassword.textinputForm.error = error
            } else {

                textUserPassword.textinputForm.error = null
            }
        }

        registerViewModel.loadingProgress.observe(this) { loadState ->
            when (loadState) {

                is LoadState.Loading -> {
                    Log.i("Teste", "Logging")
                    LoadingActivity.start(this)
                }

                is LoadState.NotLoading -> {
                    Log.e("Teste", "Not Logging")
                }

                is LoadState.Error -> {
                    Log.e("Teste", "Error")
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
}