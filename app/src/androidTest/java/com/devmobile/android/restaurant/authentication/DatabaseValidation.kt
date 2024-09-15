package com.devmobile.android.restaurant.authentication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.local.IUserDao
import com.devmobile.android.restaurant.model.repository.local.RestaurantLocalDatabase
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class) // Class for uses Android Framework
class DatabaseValidation {

    private val emailsUnregistered: Array<String> = arrayOf(
        "alice.smith@example.com",
        "bob.jones123@example.co.uk",
        "charlie.brown@webmail.net",
        "david.johnson@workplace.org",
        "emma.williams@domain.edu",
        "frank.miller@company.com",
        "grace.lee@business.org",
        "henry.clark@info.com",
        "irene.martinez@service.co",
        "jackson.white@techmail.net",
        "kelly.morris@personal.com"
    )

    private val emailsRegistered: Array<String> = arrayOf(
        "test0@gmail.com",
        "test1@gmail.com",
        "test2@gmail.com",
        "test3@gmail.com",
        "test4@gmail.com",
        "test5@gmail.com",
        "test6@gmail.com",
        "test7@gmail.com",
    )


    private lateinit var database: RestaurantLocalDatabase
    private lateinit var userDao: IUserDao

    @Before
    fun insertNewsEmailsOnDatabase() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RestaurantLocalDatabase::class.java).build()
        userDao = database.getUserDao()

        emailsRegistered.forEachIndexed { index, email ->

            val user = User((index).toLong(), "Test", "", email, "Test3232@#")
            userDao.insertUser(user)
        }
    }

    @Test
    fun check_Whether_Email_Already_Registered() {

        var result: Boolean

        emailsUnregistered.forEach { email ->

            result = userDao.amountRegisteredEmail(email) == 0

            assertTrue("Email Already Registered: $email", result)
        }

        emailsRegistered.forEach { email ->

            result = userDao.amountRegisteredEmail(email) > 0

            assertTrue("Email Unregistered: $email", result)
        }

    }

    @Test
    fun verify_Amount_Users() {

        var result: Boolean

        emailsUnregistered.forEach { email ->

            result = userDao.getQuantityOfUsers() == emailsUnregistered.size

            assertTrue("Email Already Registered: $email", result)
        }

        userDao.deleteUser(userDao.findUserByEmail("test4@gmail.com")!!)

        emailsUnregistered.forEach { email ->

            result = userDao.getQuantityOfUsers() == emailsUnregistered.size - 1

            assertTrue("Email Already Registered: $email", result)
        }
    }

    // Concurrent Possible Problems (inside-box)
    // @Test
    fun check_Synchrony_Insert() {

    }

    // @Test
    fun check_Synchrony_Delete() {

    }

    // @Test
    fun check_Synchrony_DeadLock() {

    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {

        database.close()
    }
}