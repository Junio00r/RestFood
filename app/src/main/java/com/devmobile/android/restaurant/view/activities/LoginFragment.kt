package com.devmobile.android.restaurant.view.activities

import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.model.repository.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.local.IUserDao
import com.devmobile.android.restaurant.databinding.FragmentUserAuthenticationBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : FragmentActivity(), View.OnClickListener {
    private lateinit var binding: FragmentUserAuthenticationBinding
    private lateinit var buttonSignUp: MaterialButton
    private lateinit var buttonEnter: MaterialButton
    private var userName: TextInputEditText? = null
    private var mesaNumero: TextInputEditText? = null
    private lateinit var userDao: IUserDao
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userDao = RestaurantLocalDatabase.getInstance(this).getUserDao()
        intent = Intent(this, MainActivity::class.java)

        if (userDao.getQuantityOfUsers() == 0) {

            binding = FragmentUserAuthenticationBinding.inflate(layoutInflater)
            setContentView(binding.root)
            initializeViews()
            setListeners()

        } else {

            startMenuActivity()
        }
    }

    /**
     * Ainda irei tratar os casos em que o usuário faz muitaas requisitando login/cadastro
     */
    override fun onClick(v: View) {

        when (v) {

            buttonSignUp -> showMessage("Não Implementado ainda")

            buttonEnter -> {

                if (userName!!.text.isNullOrBlank()) {

                    showMessage("Insira seu Nome")

                } else if (mesaNumero!!.text.isNullOrBlank()) {

                    showMessage("Insira o numero da mesa")

                } else {

                    insertUser(User(1, userName!!.text.toString(), "teste"))
                    startMenuActivity()
                }
            }
        }
    }

    private fun initializeViews() {

        userName = binding.editUserName
        mesaNumero = binding.editTableNumber
        buttonSignUp = binding.buttonSignup
        buttonEnter = binding.buttonEnter
    }

    private fun setListeners() {
        buttonSignUp.setOnClickListener(this)
        buttonEnter.setOnClickListener(this)
    }


    private fun startMenuActivity() {
        startActivity(intent)
        finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun insertUser(userRepository: User) {

        try {

            userDao.insertUser(userRepository)

        } catch (e: SQLiteException) {

            Log.e("DatabaseError", "Erro ao inserir dados no banco de dados", e);
        }
    }
}
