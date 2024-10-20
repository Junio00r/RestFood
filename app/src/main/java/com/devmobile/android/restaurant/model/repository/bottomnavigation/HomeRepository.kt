package com.devmobile.android.restaurant.model.repository.bottomnavigation

import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.datasource.local.IRestaurantDao.RestaurantTuple
import com.devmobile.android.restaurant.usecase.Fetch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(
    private val database: RestaurantLocalDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun fetchRestaurants(query: String): List<RestaurantTuple> {

        return withContext(ioDispatcher) {

            database.getRestaurantDao().getNameMatches(query, 4)
        }
    }

    suspend fun fetchCacheFetched(): List<String> {
        return withContext(ioDispatcher) {

            database.getCache().getCachedFetches()
        }
    }
}
