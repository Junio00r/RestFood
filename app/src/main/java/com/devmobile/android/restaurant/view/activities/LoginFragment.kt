package com.devmobile.android.restaurant.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.databinding.FragmentUserAuthenticationBinding
import com.devmobile.android.restaurant.model.repository.localdata.IUserDao
import com.devmobile.android.restaurant.model.repository.remotedata.LoginRepository
import com.devmobile.android.restaurant.viewmodel.LoginViewModel
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : FragmentActivity() {
    private lateinit var binding: FragmentUserAuthenticationBinding

    private lateinit var buttonSignUp: MaterialButton
    private lateinit var buttonSignIn: MaterialButton

    private var userEmail: TextInputEditText? = null
    private var userPassword: TextInputEditText? = null

    private lateinit var userDao: IUserDao

    private lateinit var intent: Intent

    // ViewModels
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(repository = LoginRepository(this))
    }
    private val registerActivity = RegisterFragment()

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
//    override fun onClick(v: View) {
//
//        when (v) {
//
//            buttonSignUp -> {
//
//                intent = Intent(this, RegisterFragment::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//
//            buttonSignIn -> loginViewModel.login(userEmail!!, userPassword!!)
//        }
//    }

    private fun initializeViews() {

        userEmail = binding.editUserName
        userPassword = binding.editTableNumber
        buttonSignUp = binding.buttonRegister
        buttonSignIn = binding.buttonLogin
    }

    private fun setListeners() {

        buttonSignUp.setOnClickListener {

            startRegisterActivity(Intent(this, RegisterFragment::class.java))
        }

        buttonSignIn.setOnClickListener {

            startMenuActivity(Intent(this, LoginFragment::class.java))
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

    private fun showMessage(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
