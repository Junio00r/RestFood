package com.devmobile.android.restaurant.model.repository

import android.util.Log
import com.devmobile.android.restaurant.model.datasource.local.IItemDao
import com.devmobile.android.restaurant.model.datasource.local.IRestaurantDao
import com.devmobile.android.restaurant.model.datasource.local.RemoteCacheManager
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.usecase.Converters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MenuManagerRemoteRepository private constructor(
    private val restaurantDao: IRestaurantDao,
    private val foodDao: IItemDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend fun requestSections(restaurantId: Long): List<String> {

        return withContext(ioDispatcher) {
            Converters.fromStringToList(restaurantDao.getSections(restaurantId))
        }
    }

    suspend fun requestItemsByPattern(restaurantId: Long, pattern: String): List<Item> {

        return withContext(ioDispatcher) {

            return@withContext foodDao.getNameMatches(restaurantId, pattern)
        }
    }

    suspend fun requestItemsBySections(restaurantId: Long, section: String?): List<Item> {

        return withContext(ioDispatcher) {

            if (section == null)
                return@withContext restaurantDao.getAllItems(restaurantId)
                    .also { saveInCache(it) }
            else
                return@withContext foodDao.getItemsBySection(restaurantId, section)
                    .also { saveInCache(it) }
        }
    }

    suspend fun requestItems(restaurantId: Long, foodIds: List<Long>): List<Item> {

        return withContext(ioDispatcher) {

            foodIds.filter {
                Log.d("DEBUGGING", "BUSCOU no Cache $it")
                RemoteCacheManager.get(it.toString()) == null
            }.let {
                Log.d("DEBUGGING", "BUSCOU no Banco de Dados $it")
                foodDao.getItemsById(restaurantId, it).also { saveInCache(it) }
            }
        }
    }

    private suspend fun saveInCache(data: List<Item>) {
        withContext(ioDispatcher) {
            data.onEach { RemoteCacheManager.put(it.id.toString(), it) }
        }
    }

    companion object {
        private var instance: MenuManagerRemoteRepository? = null

        fun getInstance(
            restaurantDao: IRestaurantDao,
            foodDao: IItemDao,
            ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        ): MenuManagerRemoteRepository {

            return instance ?: synchronized(this) {
                instance ?: MenuManagerRemoteRepository(restaurantDao, foodDao, ioDispatcher)
                    .also { instance = it }
            }
        }
    }
}
