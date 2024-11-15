package com.devmobile.android.restaurant.model.repository

import com.devmobile.android.restaurant.model.datasource.local.IFoodDao
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.model.repository.datasource.local.IRestaurantDao
import com.devmobile.android.restaurant.usecase.Converters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodChoiceRepository private constructor(
    private val restaurantDao: IRestaurantDao,
    private val foodDao: IFoodDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    companion object {
        private var instance: FoodChoiceRepository? = null

        fun getInstance(
            restaurantDao: IRestaurantDao,
            foodDao: IFoodDao,
            ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
        ): FoodChoiceRepository {

            return instance ?: synchronized(this) {
                instance ?: FoodChoiceRepository(restaurantDao, foodDao, ioDispatcher).also { instance = it }
            }
        }
    }

    suspend fun requestSections(restaurantId: Long): ArrayList<String> {

        return withContext(ioDispatcher) {
            Converters.fromStringToList(restaurantDao.getSections(restaurantId))
        }
    }

    suspend fun requestFoodsByPattern(pattern: String): List<Food> {

        return withContext(ioDispatcher) {

            return@withContext foodDao.getNameMatches(pattern)
        }
    }


    suspend fun requestFoodsBySections(restaurantId: Long, section: String?): List<Food> {

        return withContext(ioDispatcher) {

            if (section == null) {

                return@withContext restaurantDao.getAllFoods(restaurantId)
            } else {
                return@withContext foodDao.getFoodsBySection(restaurantId, section)
            }
        }
    }

    suspend fun requestFood(restaurantId: Long, foodIds: List<Long>): List<Food> {

        return withContext(ioDispatcher) {

            foodDao.getFoodsById(restaurantId, foodIds)
        }
    }
}
