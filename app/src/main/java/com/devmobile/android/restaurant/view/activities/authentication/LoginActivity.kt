package com.devmobile.android.restaurant.view.activities.authentication

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.databinding.FragmentLoginBinding
import com.devmobile.android.restaurant.model.repository.authentication.LoginRepository
import com.devmobile.android.restaurant.model.datasource.local.IUserDao
import com.devmobile.android.restaurant.viewmodel.authentication.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : FragmentActivity() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var buttonSignUp: MaterialButton
    private lateinit var buttonSignIn: MaterialButton

    private var userEmail: TextInputEditText? = null
    private var userPassword: TextInputEditText? = null

    private lateinit var userDao: IUserDao

    private lateinit var intent: Intent

    // ViewModels
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModel.provideFactory(
            repository = LoginRepository(this) /* Still will modify */,
            owner = this@LoginActivity
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        setListeners()

    }

    private fun initializeViews() {

        userEmail = binding.editUserName
        userPassword = binding.editTableNumber
        buttonSignUp = binding.buttonRegister
        buttonSignIn = binding.buttonLogin
    }

    private fun setListeners() {

        buttonSignUp.setOnClickListener {

            startRegisterActivity(Intent(this, FormActivity::class.java))
        }

        buttonSignIn.setOnClickListener {

            startMenuActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun startRegisterActivity(intent: Intent) {

        startActivity(intent)
    }

    private fun startMenuActivity(intent: Intent) {

        loginViewModel.login(userEmail!!, userPassword!!)

        startActivity(intent)
        finish()
    }
}

