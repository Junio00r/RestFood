package com.devmobile.android.restaurant

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.databinding.FragmentLoginBinding
import com.google.android.material.button.MaterialButton
import java.io.FilterInputStream

class LoginFragment : FragmentActivity(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var buttonSignUp: MaterialButton
    private lateinit var buttonEnter: MaterialButton
    private var userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {

            super.onCreate(savedInstanceState)
            binding = FragmentLoginBinding.inflate(layoutInflater)

            setContentView(binding.root)

            init()
        } else {

            userName = savedInstanceState.getString("GAME_STATE_KEY")
            val intent = Intent(this, MenuActivity::class.java)

            startActivity(intent)
            this.onDestroy()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)
    }

    private fun init() {

        initializeVariable()
        setClickListener()
        setInputFilter()
    }

    /**
     * Ainda irei tratar os casos em que o usuário faz muitos requisitando login/cadastro
     */
    override fun onClick(v: View) {

        when (v) {

            buttonSignUp -> {

                Toast.makeText(this, "Não Implementado", Toast.LENGTH_LONG).show()
            }

            buttonEnter -> {

                val intent = Intent(this, MenuActivity::class.java)

                startActivity(intent)
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
}
