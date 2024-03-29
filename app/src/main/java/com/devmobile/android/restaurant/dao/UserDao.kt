package com.devmobile.android.restaurant.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.restaurant.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    /**
     *  @return an value of lines quantity deleted
     */
    @Delete
    fun deleteUser(user: User)

    /**
     *  @return an value of lines quantity updated
     */
    @Update
    fun updateUser(user: User)

    /**
     * Esses métodos serão alterados. Essa lógica está fora do app
     */

    @Query("SELECT * FROM users WHERE id = :userId")
    fun findUserById(userId: Long): User?

    @Query("SELECT * FROM users WHERE name = :userName")
    fun findUserByName(userName: String): User?

    @Query("SELECT name FROM users LIMIT 1")
    fun getUserName(): String?

    @Query("SELECT * FROM users")
    fun getUsers(): List<User>
}