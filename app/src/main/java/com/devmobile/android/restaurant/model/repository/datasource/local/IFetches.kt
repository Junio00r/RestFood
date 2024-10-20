package com.devmobile.android.restaurant.model.repository.datasource.local

import androidx.room.Dao
import androidx.room.Query
import com.devmobile.android.restaurant.usecase.Fetches

@Dao
interface IFetches {

    @Query("DELETE FROM fetches")
    suspend fun clearCache()

    @Query("SELECT fetch FROM fetches")
    suspend fun getFetchesCache(): List<Fetches>
}