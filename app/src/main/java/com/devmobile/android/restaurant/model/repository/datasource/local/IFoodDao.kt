package com.devmobile.android.restaurant.model.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.usecase.enums.FoodSection

@Dao
interface IFoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(foods: List<Food>)

    /**
     *  @return a value of lines quantity deleted
     */
    @Delete
    fun deleteAnyFoods(foods: List<Food>)

    /**
     *  @return a value of lines quantity updated
     */
    @Update
    fun updateFood(foods: List<Food>)

    /*
     * Métodos de consultas usado para instrucoes mais especificas e complexas
     * O Room avalia as consultas no momento da compilação, evitando problemas
     * no momento de execucao, caso tenha um erro de compilação é lançado.
     **/

    /**
     * Retrieves a list of Food items with the same section as specified by [foodSection].
     *
     * @param foodSection the section to search for in the database
     * @return a list of Food items with the specified section, or null if not found
     */
    @Query("SELECT * FROM foods WHERE section = :foodSection")
    fun getFoodsBySection(foodSection: FoodSection): List<Food?>

    /**
     * Retrieves a Food item with the specified ID.
     *
     * @param foodId the ID of the food to retrieve
     * @return the Food item with the specified ID, or null if not found
     */
    @Query("SELECT * FROM foods WHERE id = :foodId")
    fun getFoodById(foodId: Long): Food?

    /**
     * Retrieves the total number of foods in the database.
     *
     * @return the total number of foods in the database
     */
    @Query("SELECT COUNT(id) FROM foods")
    fun getQuantityOfFoods(): Int

    @Query(
        "SELECT restaurants.id, foods.* FROM foods " +
                "LEFT JOIN restaurants " +
                "WHERE :restaurantId = restaurants.id AND :restaurantId = foods.restaurantId"
    )
    suspend fun getAllFoods(restaurantId: Long): List<Food?>

    @Query(
        "SELECT restaurants.id, * FROM restaurants " +
                "LEFT JOIN foods " +
                "WHERE id == :restaurantId AND :section = foods.section"
    )
    suspend fun getFoodsForSection(restaurantId: Long, section: String): List<Food>

    /**
     * Deletes all foods stored in the database.
     */
    @Query("DELETE FROM foods")
    fun deleteAllTable()
}
