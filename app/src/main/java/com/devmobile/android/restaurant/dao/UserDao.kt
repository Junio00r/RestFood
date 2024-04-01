package com.devmobile.android.restaurant.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.restaurant.User

/**
 * Data Access Object (DAO) for the User entity.
 * This interface provides methods to interact with the users table in the database.
 */
@Dao
interface UserDao {

    /**
     * Inserts a new user into the database.
     * If there is a conflict with an existing user, the insertion will be ignored.
     *
     * @param user The user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    /**
     * Deletes a user from the database.
     *
     * @param user The user to be deleted.
     * @return The number of rows affected by the delete operation.
     */
    @Delete
    fun deleteUser(user: User): Int

    /**
     * Updates an existing user in the database.
     *
     * @param user The user with the updated information.
     * @return The number of rows affected by the update operation.
     */
    @Update
    fun updateUser(user: User): Int

    /**
     * Retrieves a user from the database based on the ID.
     *
     * @param userId The ID of the user to be retrieved.
     * @return The user with the specified ID or null if not found.
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    fun findUserById(userId: Long): User?

    /**
     * Retrieves a user from the database based on the name.
     *
     * @param userName The name of the user to be retrieved.
     * @return The user with the specified name or null if not found.
     */
    @Query("SELECT * FROM users WHERE name = :userName")
    fun findUserByName(userName: String): User?

    /**
     * Retrieves the name of the first user in the database.
     *
     * @return The name of the first user or null if the database is empty.
     */
    @Query("SELECT name FROM users LIMIT 1")
    fun getUserName(): String?

    /**
     * Retrieves all users from the database.
     *
     * @return A list containing all users stored in the database.
     */
    @Query("SELECT * FROM users")
    fun getUsers(): List<User>
}