package com.devmobile.android.restaurant.model.repository.bottomnavigation

import com.devmobile.android.restaurant.model.repository.datasource.local.IFetchDao
import com.devmobile.android.restaurant.model.repository.datasource.local.IRestaurantDao
import com.devmobile.android.restaurant.usecase.Fetch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(
    private val fetchDao: IFetchDao,
    private val restaurantDao: IRestaurantDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun fetchRestaurants(query: String): List<String> {

        return withContext(ioDispatcher) {

            restaurantDao.getNameMatches(query, 4)
        }
    }

    suspend fun fetchCacheFetched(): List<String> {

        return withContext(ioDispatcher) {

            fetchDao.getCachedFetches()
        }
    }

    suspend fun removeFromCache(query: String) {

        withContext(ioDispatcher) {

            fetchDao.removeFetchByName(query)
        }
    }

    suspend fun saveFetchInCache(query: String) {

        withContext(ioDispatcher) {

            fetchDao.insert(Fetch(fetchName = query))
        }
    }
}
