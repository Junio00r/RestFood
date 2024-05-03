package com.devmobile.android.restaurant.model.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.restaurant.model.User

/**
 * Data Access Object (DAO) for the User entity.
 * This interface provides methods to interact with the users table in the database.
 */
@Dao
interface IUserDao {

    /**
     * Inserts a new user into the database.
     * If there is a conflict with an existing user, the insertion will be ignored.
     *
     * @param userRepository The user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userRepository: User)

    /**
     * Deletes a user from the database.
     *
     * @param userRepository The user to be deleted.
     * @return The number of rows affected by the delete operation.
     */
    @Delete
    fun deleteUser(userRepository: User): Int

    /**
     * Updates an existing user in the database.
     *
     * @param userRepository The user with the updated information.
     * @return The number of rows affected by the update operation.
     */
    @Update
    fun updateUser(userRepository: User): Int

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
     * Retrieves the total number of users in the database.
     *
     * @return the total number of users in the database
     */
    @Query("SELECT COUNT(id) FROM users")
    fun getQuantityOfUsers(): Int

    /**
     * Retrieves all users from the database.
     *
     * @return A list containing all users stored in the database.
     */
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>
}