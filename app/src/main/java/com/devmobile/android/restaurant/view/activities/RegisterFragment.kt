package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.FragmentRegisterUserBinding
import com.devmobile.android.restaurant.model.repository.remotedata.RegisterRepository
import com.devmobile.android.restaurant.viewmodel.RegisterViewModel
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : FragmentActivity() {
    private lateinit var registerBinding: FragmentRegisterUserBinding
    private lateinit var inputData: Array<TextInputLayout>

    private val registerRepository = RegisterRepository(this)
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(registerRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBinding = DataBindingUtil.setContentView(this, R.layout.fragment_register_user)
        registerBinding.registerView = this

        subscribeObservables()
    }

    private fun subscribeObservables() {

        registerViewModel.userNameError.observe(this) {

            if (it != RegisterViewModel.VALID_DATA) {
                registerBinding.textUserName.textinputForm.error = it
            } else {
                registerBinding.textUserName.textinputForm.error = null
            }
        }

        registerViewModel.userEmailError.observe(this) {

            if (it != RegisterViewModel.VALID_DATA) {

                registerBinding.textEmail.textinputForm.error = it
            } else {

                registerBinding.textEmail.textinputForm.error = null
            }
        }

        registerViewModel.userPasswordError.observe(this) {

            if (it != RegisterViewModel.VALID_DATA) {
                registerBinding.textUserPassword.textinputForm.error = it
            } else {
                registerBinding.textUserPassword.textinputForm.error = null
            }
        }
    }

    @CalledFromXML

    fun register() {

        val userName = registerBinding.textUserName.textinputForm.editText?.text.toString()
        val userLastName = registerBinding.textUserLastName.textinputForm.editText?.text.toString()
        val userEmail = registerBinding.textEmail.textinputForm.editText?.text.toString()
        val userPassword = registerBinding.textUserPassword.textinputForm.editText?.text.toString()

        registerViewModel.register(userName, userLastName, userEmail, userPassword)
    }

    @CalledFromXML
    fun cancelRegister() {

        finish()
    }
}