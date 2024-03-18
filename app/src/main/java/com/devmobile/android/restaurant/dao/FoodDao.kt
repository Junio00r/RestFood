package com.devmobile.android.restaurant.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.enums.FoodSection

@Dao
interface FoodDao {

    @Query("SELECT * FROM food")
    fun getAllFoods(): List<Food>

    @Query("SELECT COUNT(mId) FROM food")
    fun getFoodsSize(): Int

    @Query("SELECT * FROM food WHERE mId LIKE :foodId LIMIT 1")
    fun getFoodById(foodId: Int): Food

    @Query("SELECT * from food WHERE section LIKE :foodSection")
    fun getFoodsBySection(foodSection: FoodSection): List<Food>

    @Insert
    fun insertAll(food: List<Food>)

    @Delete
    fun deleteAll(food: List<Food>)
}