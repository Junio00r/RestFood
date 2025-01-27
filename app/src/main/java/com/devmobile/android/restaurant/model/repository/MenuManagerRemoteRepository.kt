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

class ItemChoiceRepository private constructor(
    private val restaurantDao: IRestaurantDao,
    private val foodDao: IItemDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    companion object {
        private var instance: ItemChoiceRepository? = null

        fun getInstance(
            restaurantDao: IRestaurantDao,
            foodDao: IItemDao,
            ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        ): ItemChoiceRepository {

            return instance ?: synchronized(this) {
                instance ?: ItemChoiceRepository(
                    restaurantDao,
                    foodDao,
                    ioDispatcher
                ).also { instance = it }
            }
        }
    }

    suspend fun requestSections(restaurantId: Long): ArrayList<String> {

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

            if (section == null) {

                return@withContext restaurantDao.getAllItems(restaurantId)
            } else {
                return@withContext foodDao.getItemsBySection(restaurantId, section)
            }
        }
    }

    suspend fun requestItems(restaurantId: Long, foodIds: List<Long>): List<Item> {

        return withContext(ioDispatcher) {

            foodIds.filterNot {
                Log.d("DEBUGGING", "BUSCOU no Cache $it")
                RemoteCacheManager.get(it.toString()) != null
            }
                .let {
                    Log.d("DEBUGGING", "BUSCOU no Banco de Dados $it")
                    foodDao.getItemsById(restaurantId, foodIds)
                }

        }
    }
}
