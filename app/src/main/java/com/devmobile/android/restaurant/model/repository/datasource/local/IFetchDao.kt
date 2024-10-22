package com.devmobile.android.restaurant.model.repository.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmobile.android.restaurant.usecase.Fetch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface IFetchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fetches: List<Fetch>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fetch: Fetch)

    @Query("DELETE FROM fetches")
    suspend fun clearCache()

    @Query("DELETE FROM fetches WHERE fetch = :name")
    suspend fun removeFetchByName(name: String)

    @Query("SELECT fetch FROM fetches")
    fun getCachedFetches(): Flow<List<String>>

    @Query("SELECT COUNT(fetch) FROM fetches")
    fun getCacheSize(): Int
}