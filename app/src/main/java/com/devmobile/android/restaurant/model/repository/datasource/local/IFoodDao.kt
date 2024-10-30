package com.devmobile.android.restaurant.model.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.restaurant.model.datasource.local.entities.Food

@Dao
interface IFoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(foods: List<Food>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(food: Food)

    /**
     *  @return a value of lines quantity deleted
     */
    @Delete
    suspend fun deleteFoods(foods: List<Food>)

    /**
     *  @return a value of lines quantity updated
     */
    @Update
    suspend fun updateFood(foods: List<Food>)

    @Query(
        "SELECT  * FROM foods " +
                "WHERE foods.restaurantId  = :restaurantId"
    )
    suspend fun getAllFoods(restaurantId: Long): List<Food>

    /**
     * Retrieves a list of Food items with the same section as specified by [foodSection].
     *
     * @param foodSection the section to search for in the database
     * @return a list of Food items with the specified section, or null if not found
     */
    @Query(
        "SELECT * FROM foods " +
                "WHERE foods.restaurantId = :restaurantId AND foods.section = :foodSection"
    )
    suspend fun getFoodsBySection(restaurantId: Long, foodSection: String): List<Food>

    /**
     * Retrieves a Food item with the specified ID.
     *
     * @param foodId the ID of the food to retrieve
     * @return the Food item with the specified ID, or null if not found
     */
    @Query("SELECT * FROM foods WHERE foods.restaurantId = :restaurantId AND id = :foodId")
    suspend fun getFoodById(restaurantId: Long, foodId: Long): Food

    /**
     * Retrieves the total number of foods in the database.
     *
     * @return the total number of foods in the database
     */
    @Query("SELECT COUNT(id) FROM foods")
    suspend fun getQuantityOfFoods(): Int
}
