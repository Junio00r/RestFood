package com.devmobile.android.restaurant.view.activities.authentication

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devmobile.android.restaurant.usecase.CalledFromXML
import com.devmobile.android.restaurant.usecase.RequestState
import com.devmobile.android.restaurant.databinding.FragmentLoginBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.LoginRepository
import com.devmobile.android.restaurant.view.activities.bottomnavigation.BottomNavigationActivity
import com.devmobile.android.restaurant.viewmodel.authentication.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var _binding: FragmentLoginBinding
    private val _viewModel: LoginViewModel by viewModels {
        LoginViewModel.provideFactory(
            repository = LoginRepository(localDatabase = RestaurantLocalDatabase.getInstance(this@LoginActivity)),
            owner = this@LoginActivity,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FragmentLoginBinding.inflate(this.layoutInflater)
        setContentView(_binding.root)

        _binding.loginViewModel = _viewModel
        _binding.loginActivity = this

        subscribeObserver()
    }

    private fun subscribeObserver() {

        _binding.textInputEmail.doAfterTextChanged {
            _viewModel.onEmailChanged(it.toString())
        }

        _binding.textInputPassword.doAfterTextChanged {
            _viewModel.onPasswordChanged(it.toString())
        }

        _viewModel.errorDataPropagator.observe(this@LoginActivity) { errorCause ->

            _binding.textInputEmail.error = errorCause

            _binding.textInputPassword.error = errorCause
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                _viewModel.requestLoginResult.collect { value ->

                    handleResulState(value)
                }
            }
        }
    }

    private fun handleResulState(result: RequestResult) {

        when (result) {

            is RequestResult.Success -> {
                startActivity(Intent(this@LoginActivity, BottomNavigationActivity::class.java))
            }

            is RequestResult.Error -> {
                // Nothing
            }
        }
    }

    @CalledFromXML
    fun startRegister() {

        startActivity(Intent(this, FormActivity::class.java))
    }

    @CalledFromXML
    fun requestLogin() {

        _viewModel.loginTrigger()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        _binding.textInputEmail.append(_viewModel.userEmail)
        _binding.textInputPassword.append(_viewModel.userPassword)
    }
}

