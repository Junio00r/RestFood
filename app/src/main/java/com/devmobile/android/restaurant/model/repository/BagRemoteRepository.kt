package com.devmobile.android.restaurant.model.repository

import com.devmobile.android.restaurant.model.datasource.local.IItemDao
import com.devmobile.android.restaurant.model.datasource.local.RemoteCacheManager
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemSelectedRemoteRepository(
    private val itemDao: IItemDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun addItem(itemId: List<ItemToBag>, restaurantId: Long) {
        withContext(ioDispatcher) {

        }
    }

    suspend fun requestItem(restaurantId: Long, itemId: Long): Item {

        return withContext(ioDispatcher) {

            (RemoteCacheManager.get(itemId.toString())
                ?: itemDao.getItemsById(restaurantId, listOf(itemId))
                .also { RemoteCacheManager.put(it.first().id.toString(), it.first()) }
            ) as Item
        }
    }

    data class ItemToBag(val itemId: Long, val quantity: Long, val observation: String? = null)
}
