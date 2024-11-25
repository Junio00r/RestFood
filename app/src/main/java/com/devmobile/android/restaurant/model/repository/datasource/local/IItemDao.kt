package com.devmobile.android.restaurant.model.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.restaurant.model.datasource.local.entities.Item

@Dao
interface IFoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(foods: List<Item>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(food: Item)

    /**
     *  @return a value of lines quantity deleted
     */
    @Delete
    suspend fun deleteFoods(foods: List<Item>)

    /**
     *  @return a value of lines quantity updated
     */
    @Update
    suspend fun updateFood(foods: List<Item>)

    @Query(
        "SELECT  * FROM foods " +
                "WHERE foods.restaurantId  = :restaurantId"
    )
    suspend fun getAllFoods(restaurantId: Long): List<Item>

    @Query("SELECT * FROM foods WHERE name LIKE '%' || :foodName || '%' AND foods.restaurantId = :restaurantId ORDER BY name ASC")
    suspend fun getNameMatches(restaurantId: Long, foodName: String): List<Item>

    /**
     * Retrieves a list of Food items with the same section as specified by [foodSection].
     *
     * @param foodSection the section to search for in the database
     * @return a list of Food items with the specified section, or null if not found
     */
    @Query(
        "SELECT * FROM foods WHERE foods.restaurantId = :restaurantId AND foods.section = :foodSection"
    )
    suspend fun getFoodsBySection(restaurantId: Long, foodSection: String): List<Item>

    /**
     * Retrieves a Food item with the specified ID.
     *
     * @param foodId the ID of the food to retrieve
     * @return the Food item with the specified ID, or null if not found
     */
    @Query("SELECT * FROM foods WHERE foods.restaurantId = :restaurantId AND foods.id IN (:foodId)")
    suspend fun getFoodsById(restaurantId: Long, foodId: List<Long>): List<Item>

    /**
     * Retrieves the total number of foods in the database.
     *
     * @return the total number of foods in the database
     */
    @Query("SELECT COUNT(id) FROM foods")
    suspend fun getQuantityOfFoods(): Int
}
