package com.devmobile.android.restaurant.model.repository.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmobile.android.restaurant.usecase.Fetch

@Dao
interface IFetches {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fetches: List<Fetch>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fetch: Fetch)

    @Query("DELETE FROM fetches")
    suspend fun clearCache()

    @Query("DELETE FROM fetches WHERE fetch = :name")
    suspend fun removeFetchByName(name: String)

    @Query("SELECT fetch FROM fetches")
    suspend fun getCachedFetches(): List<String>
}