package com.devmobile.android.restaurant.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.databinding.FragmentUserAuthenticationBinding
import com.devmobile.android.restaurant.model.repository.localdata.IUserDao
import com.devmobile.android.restaurant.model.repository.remotedata.LoginRepository
import com.devmobile.android.restaurant.viewmodel.LoginViewModel
import com.devmobile.android.restaurant.viewmodel.LoginViewModelFactory
import com.devmobile.android.restaurant.viewmodel.RegisterViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : FragmentActivity(), View.OnClickListener {
    private lateinit var binding: FragmentUserAuthenticationBinding

    private lateinit var buttonSignUp: MaterialButton
    private lateinit var buttonSignIn: MaterialButton

    private var userEmail: TextInputEditText? = null
    private var userPassword: TextInputEditText? = null

    private lateinit var userDao: IUserDao

    private lateinit var intent: Intent

    // ViewModels
    private val loginViewModel: LoginViewModel by viewModels() {
        LoginViewModelFactory(loginRepository = LoginRepository(this))
    }
    private val registerActivity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentUserAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        setListeners()

    }

    /**
     * Ainda irei tratar os casos em que o usuário faz muitaas requisicoes login/cadastro
     */
    override fun onClick(v: View) {

        when (v) {

            buttonSignUp -> registerActivity!!

            buttonSignIn -> loginViewModel.login(userEmail!!, userPassword!!)
        }
    }

    private fun initializeViews() {

        userEmail = binding.editUserName
        userPassword = binding.editTableNumber
        buttonSignUp = binding.buttonRegister
        buttonSignIn = binding.buttonLogin
    }

    private fun setListeners() {

        buttonSignUp.setOnClickListener(this)
        buttonSignIn.setOnClickListener(this)
    }


    private fun startMenuActivity() {
        startActivity(intent)
        finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
