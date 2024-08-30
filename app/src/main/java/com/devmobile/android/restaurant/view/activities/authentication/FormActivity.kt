package com.devmobile.android.restaurant.view.activities.authentication

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.devmobile.android.restaurant.CalledFromXML
import com.devmobile.android.restaurant.IShowError
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.RequestResult
import com.devmobile.android.restaurant.databinding.ActivityFormDataBinding
import com.devmobile.android.restaurant.extensions.maxLength
import com.devmobile.android.restaurant.model.repository.authentication.FormRepository
import com.devmobile.android.restaurant.view.customelements.TextInput
import com.devmobile.android.restaurant.viewmodel.ViewModelFactory
import com.devmobile.android.restaurant.viewmodel.authentication.FormViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormActivity : AppCompatActivity(), IShowError {

    private lateinit var _formBinding: ActivityFormDataBinding

    private val formRepository = FormRepository(this@FormActivity)

    // I prefer to use the SavedStateHandle to practices
    private val _formViewModel: FormViewModel by viewModels {
        ViewModelFactory(
            repository = formRepository,
            ownerOfStateToSave = this@FormActivity,
            defaultValuesForNulls = null
        )
    }

    private val dataEditText = ArrayList<TextInputLayout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _formBinding = ActivityFormDataBinding.inflate(layoutInflater)
        setContentView(_formBinding.root)

        _formBinding.registerView = this@FormActivity

        dataEditText.addAll(

            listOf(
                _formBinding.inputUserName.getChildAt(0) as TextInputLayout,
                _formBinding.inputUserLastName.getChildAt(0) as TextInputLayout,
                _formBinding.inputUserEmail.getChildAt(0) as TextInputLayout,
                _formBinding.inputUserPassword.getChildAt(0) as TextInputLayout
            )
        )

        Log.d("ViewModel", "onCreate ")
        Log.d(
            "ViewModel",
            "\nvalues: NAME:${_formViewModel.userName}  LASTNAME:${_formViewModel.userLastName}  EMAIL:${_formViewModel.userEmail}  PASSWORD:${_formViewModel.userPassword}  "
        )
        Log.d("ViewModel", "\nviewModel: $_formViewModel")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("ViewModel", "onRestart")
        Log.d(
            "ViewModel",
            "\nvalues: NAME:${_formViewModel.userName}  LASTNAME:${_formViewModel.userLastName}  EMAIL:${_formViewModel.userEmail}  PASSWORD:${_formViewModel.userPassword}  "
        )
        Log.d("ViewModel", "\nviewModel: $_formViewModel")
    }

    override fun onStart() {
        super.onStart()
        // call before changed
        Log.d("ViewModel", "onStart")
        Log.d(
            "ViewModel",
            "\nvalues: NAME:${_formViewModel.userName}  LASTNAME:${_formViewModel.userLastName}  EMAIL:${_formViewModel.userEmail}  PASSWORD:${_formViewModel.userPassword}  "
        )
        Log.d("ViewModel", "\nviewModel: $_formViewModel")
    }

    override fun onResume() {
        super.onResume()
        setUpInputEditText()
        subscribeObservables()
        Log.d("ViewModel", "onResume")
        Log.d(
            "ViewModel",
            "\nvalues: NAME:${_formViewModel.userName}  LASTNAME:${_formViewModel.userLastName}  EMAIL:${_formViewModel.userEmail}  PASSWORD:${_formViewModel.userPassword}  "
        )
        Log.d("ViewModel", "\nviewModel: $_formViewModel")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ViewModel", "onPause")
        Log.d(
            "ViewModel",
            "\nvalues: NAME:${_formViewModel.userName}  LASTNAME:${_formViewModel.userLastName}  EMAIL:${_formViewModel.userEmail}  PASSWORD:${_formViewModel.userPassword}  "
        )
        Log.d("ViewModel", "\nviewModel: $_formViewModel")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ViewModel", "onStop")
        Log.d(
            "ViewModel",
            "\nvalues: NAME:${_formViewModel.userName}  LASTNAME:${_formViewModel.userLastName}  EMAIL:${_formViewModel.userEmail}  PASSWORD:${_formViewModel.userPassword}  "
        )
        Log.d("ViewModel", "\nviewModel: $_formViewModel")
    }

    private fun setUpInputEditText() {

        _formBinding.apply {

            val anyHasFocus = dataEditText.any { it.hasFocus() }

            if (!anyHasFocus) {
                dataEditText[0].requestFocus()
            }

            // Set Hints
            dataEditText[0].hint = "Name *"
            dataEditText[1].hint = "Lastname"
            dataEditText[2].hint = "Email *"
            dataEditText[3].hint = "Password *"

            // Setup InputType
//            inputUserName.editText?.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
//            inputUserLastName.editText?.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
//            inputUserEmail.editText?.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
//            inputUserPassword.editText?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//
//            inputUserEmail.counterMaxLength = 256
//
//            // Setup icon
//            // inputUserPassword.textInputForm.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
//
//            // Enable Counter
//            inputUserPassword.isCounterEnabled = true

        }
    }

    private fun subscribeObservables() {

        with(_formBinding) {

            // Errors observables
            _formViewModel.nameErrorPropagator.observe(this@FormActivity) { error ->

                dataEditText[0].error = error
            }

            _formViewModel.lastNameErrorPropagator.observe(this@FormActivity) { error ->

                dataEditText[1].error = error
            }

            _formViewModel.emailErrorPropagator.observe(this@FormActivity) { error ->

                dataEditText[2].error = error
            }

            _formViewModel.passwordErrorPropagator.observe(this@FormActivity) { error ->

                dataEditText[3].error = error
            }

            _formViewModel.resultRequestData.observe(this@FormActivity) { resultOfRequest ->

//                handleLoadState(resultOfRequest)
            }

            // Listeners after text changed
//            inputUserName.editText?.doAfterTextChanged { Log.d("ViewModel", "CHAMOU NAME")
//                    _formViewModel.onNameChanged(it.toString())
//            }
//
//            inputUserLastName.editText?.doAfterTextChanged {
//                Log.d("ViewModel", "CHAMOU LASTNAME")
//                _formViewModel.onLastNameChanged(it.toString())
//            }
//
//            inputUserEmail.editText?.doAfterTextChanged {
//                Log.d("ViewModel", "CHAMOU EMAIL")
//                _formViewModel.onEmailChanged(it.toString())
//            }
//
//            inputUserPassword.editText?.doAfterTextChanged {
//                Log.d("ViewModel", "CHAMOU PASSWORD")
//                _formViewModel.onPasswordChanged(it.toString())
//            }
            Log.d("ViewModel", "-------------------------------------")
        }
    }

    private fun handleLoadState(requestOfResult: RequestResult?) {

        when (requestOfResult) {


            is RequestResult.Success -> {

                startActivity(Intent(this@FormActivity, VerificationActivity::class.java))
            }

            is RequestResult.Error -> {

                showErrorMessage(requestOfResult.exception.message ?: "Register Error")
            }

            else -> {

                // Nothing
            }
        }
    }

    @CalledFromXML
    fun nextRegister() {

        _formViewModel.registerTrigger()
    }

    @CalledFromXML
    fun cancelRegister() {

        finish()
    }

    override fun showErrorMessage(errorMessage: String) {

        val mySnackBar = Snackbar.make(_formBinding.registerContainer, errorMessage, 2000)

        mySnackBar.setActionTextColor(ColorStateList.valueOf(this.getColor(R.color.green_light_one)))
        mySnackBar.setAction("OK") {
            mySnackBar.dismiss()
        }.show()
    }
}