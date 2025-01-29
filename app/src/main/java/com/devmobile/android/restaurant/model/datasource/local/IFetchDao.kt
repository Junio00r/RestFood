package com.devmobile.android.restaurant.model.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmobile.android.restaurant.usecase.entities.Fetch

@Dao
interface IFetchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fetches: List<Fetch>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fetch: Fetch)

    @Query("DELETE FROM fetches")
    suspend fun clearCache()

    @Query("DELETE FROM fetches WHERE fetchName = :name")
    suspend fun removeFetchByName(name: String)

    @Query("SELECT fetchName FROM fetches")
    suspend fun getCachedFetches(): List<String>

    @Query("SELECT COUNT(fetchName) FROM fetches")
    suspend fun getCacheSize(): Int
}