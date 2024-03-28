package com.devmobile.android.restaurant.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.RestaurantDatabase
import com.devmobile.android.restaurant.enums.FoodSection

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(foods: List<Food>)

    /**
     *  @return an value of lines quantity deleted
     */
    @Delete
    fun deleteAll(foods: List<Food>)

    /**
     *  @return an value of lines quantity updated
     */
    @Update
    fun updateFood(foods: List<Food>)

    // Métodos de consultas usado para instrucoes mais especificas e complexas //
    // O Room avalia as consultas no momento da compilação, evitando problemas
    // no momento de execucao, caso tenha um erro de compilação é lançado.
    @Query("SELECT * FROM foods WHERE section = :foodSection")
    fun getFoodsBySection(foodSection: FoodSection): List<Food?>

    @Query("SELECT * FROM foods WHERE mId = :foodId")
    fun getFoodById(foodId: Long): Food?

    @Query("SELECT COUNT(mId) FROM foods")
    fun getFoodsSize(): Int

    @Query("SELECT * FROM foods")
    fun getAllFoods(): List<Food?>

    @Query("DELETE FROM foods")
    fun deleteAllTable()
}
