package com.devmobile.android.restaurant.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.User
import com.devmobile.android.restaurant.enums.FoodSection

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

    // Métodos de consultas usado para instrucoes mais especificas e complexas //
    // O Room avalia as consultas no momento da compilação, evitando problemas
    // no momento de execucao, caso tenha um erro de compilação é lançado.

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getFoodById(userId: Long): User?
}