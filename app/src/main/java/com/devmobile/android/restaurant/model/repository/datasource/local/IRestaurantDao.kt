package com.devmobile.android.restaurant.model.repository.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.usecase.entities.Restaurant

@Dao
interface IRestaurantDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(restaurant: List<Restaurant>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(restaurant: Restaurant)

    @Query("SELECT * from restaurants")
    suspend fun getAll(): List<Restaurant?>

    @Query("SELECT * from restaurants WHERE id = :restaurantId")
    suspend fun getById(restaurantId: Long): Restaurant?

    @Query("SELECT name FROM restaurants WHERE name LIKE '%' || :searchName || '%' LIMIT :limit")
    suspend fun getNameMatches(searchName: String, limit: Int = 20): List<String>

    @Query("SELECT sections from restaurants WHERE id = :restaurantId")
    suspend fun getSections(restaurantId: Long): String

    @Query(
        "SELECT * FROM items " +
                "WHERE :restaurantId = items.restaurantId"
    )
    suspend fun getAllItems(restaurantId: Long): List<Item>
}
