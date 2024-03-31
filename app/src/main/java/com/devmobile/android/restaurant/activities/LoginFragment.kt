package com.devmobile.android.restaurant.activities

import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.RestaurantDatabase
import com.devmobile.android.restaurant.User
import com.devmobile.android.restaurant.dao.UserDao
import com.devmobile.android.restaurant.databinding.FragmentLoginBinding
import com.google.android.material.button.MaterialButton

class LoginFragment : FragmentActivity(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var buttonSignUp: MaterialButton
    private lateinit var buttonEnter: MaterialButton
    private var userName: String? = null
    private lateinit var userDao:UserDao
    private lateinit var intent:Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userDao = RestaurantDatabase.getInstance(this).getUserDao()
        intent = Intent(this, MenuActivity::class.java)

        if (userDao.getUsers().isEmpty()) {

            binding = FragmentLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            init()

        } else {

            startActivity(intent)
            finish()
        }
    }

    private fun init() {

        initializeVariable()
        setClickListener()
        setInputFilter()
    }

    /**
     * Ainda irei tratar os casos em que o usuário faz muitaas requisitando login/cadastro
     */
    override fun onClick(v: View) {

        when (v) {

            buttonSignUp -> {

                Toast.makeText(this, "Não Implementado", Toast.LENGTH_SHORT).show()
            }

            buttonEnter -> {

                if (userName.isNullOrEmpty()) {

                    Toast.makeText(this, "Insira seu Nome", Toast.LENGTH_SHORT).show()

                } else {

                    insertUser(User(1, userName!!))
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun initializeVariable() {

        userName = binding.editUserName.toString()
        buttonSignUp = binding.buttonSignup
        buttonEnter = binding.buttonEnter
    }

    private fun setClickListener() {

        buttonSignUp.setOnClickListener(this)
        buttonEnter.setOnClickListener(this)
    }

    private fun setInputFilter() {

        buttonSignUp.setOnClickListener(this)
        buttonEnter.setOnClickListener(this)
    }

    private fun insertUser(user: User) {

        try {

            userDao.insertUser(user)

        } catch (e: SQLiteException) {

            Log.e("DatabaseError", "Erro ao inserir dados no banco de dados", e);
        }
    }
}
