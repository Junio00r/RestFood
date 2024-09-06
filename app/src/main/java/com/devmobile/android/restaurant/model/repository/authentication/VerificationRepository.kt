package com.devmobile.android.restaurant.model.repository.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmobile.android.restaurant.AccountException
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.random.Random

class VerificationRepository(
    private val context: Context,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + coroutineDispatcher),
) {
    private val database = RestaurantLocalDatabase.getInstance(context)
    private var codeGenerator: Random = Random.Default
    private var currentCodeGenerated: String = ""

    private val _isCodesValid = MutableLiveData<Boolean>()
    val isCodeValid: LiveData<Boolean> = _isCodesValid

    private val _canResendCode = MutableLiveData<Boolean>()
    val canResendCode: LiveData<Boolean> = _canResendCode

    init {

        requestNewVerificationCode()
    }

    @SuppressLint("DefaultLocale")
    fun requestNewVerificationCode() {

        val codeGenerated = codeGenerator.nextInt(0, 1_000_000)
        currentCodeGenerated = String.format("%06d", codeGenerated)

        coroutineScope.launch {

            withContext(Dispatchers.IO) {

                // Uses email api to send verification code
            }
        }

        Log.i("REQUEST", "Request New Verification Code")
    }

    private suspend fun createAccount(user: User) {

        val userDao = RestaurantLocalDatabase.getInstance(context).getUserDao()

        return withContext(coroutineDispatcher) {

            try {

                userDao.insertUser(user)

            } catch (e: IOException) {

                throw e

            } catch (e: SQLiteDatabaseCorruptException) {

                throw e

            } catch (e: SQLiteException) {

                throw e

            } catch (e: AccountException) {

                throw e
            }
        }
    }

    fun isCodeValid(codeEntered: String): Boolean {

        return codeEntered == currentCodeGenerated
    }
}