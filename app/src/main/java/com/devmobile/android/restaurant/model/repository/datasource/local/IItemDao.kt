package com.devmobile.android.restaurant.model.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devmobile.android.restaurant.model.datasource.local.entities.Item

@Dao
interface IItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<Item>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    /**
     *  @return a value of lines quantity deleted
     */
    @Delete
    suspend fun deleteItems(items: List<Item>)

    /**
     *  @return a value of lines quantity updated
     */
    @Update
    suspend fun updateItem(items: List<Item>)

    @Query(
        "SELECT  * FROM items " +
                "WHERE items.restaurantId  = :restaurantId"
    )
    suspend fun getAllItems(restaurantId: Long): List<Item>

    @Query("SELECT * FROM items WHERE name LIKE '%' || :itemName || '%' AND items.restaurantId = :restaurantId ORDER BY name ASC")
    suspend fun getNameMatches(restaurantId: Long, itemName: String): List<Item>

    /**
     * Retrieves a list of Item items with the same section as specified by [itemSection].
     *
     * @param itemSection the section to search for in the database
     * @return a list of Item items with the specified section, or null if not found
     */
    @Query(
        "SELECT * FROM items WHERE items.restaurantId = :restaurantId AND items.section = :itemSection"
    )
    suspend fun getItemsBySection(restaurantId: Long, itemSection: String): List<Item>

    /**
     * Retrieves a Item item with the specified ID.
     *
     * @param itemId the ID of the item to retrieve
     * @return the Item item with the specified ID, or null if not found
     */
    @Query("SELECT * FROM items WHERE items.restaurantId = :restaurantId AND items.id IN (:itemId)")
    suspend fun getItemsById(restaurantId: Long, itemId: List<Long>): List<Item>

    /**
     * Retrieves the total number of items in the database.
     *
     * @return the total number of items in the database
     */
    @Query("SELECT COUNT(id) FROM items")
    suspend fun getQuantityOfItems(): Int
}
