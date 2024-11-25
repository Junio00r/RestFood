package com.devmobile.android.restaurant.model.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.model.datasource.local.entities.User
import com.devmobile.android.restaurant.model.repository.datasource.local.IFetchDao
import com.devmobile.android.restaurant.model.repository.datasource.local.IRestaurantDao
import com.devmobile.android.restaurant.usecase.Converters
import com.devmobile.android.restaurant.usecase.Fetch
import com.devmobile.android.restaurant.usecase.entities.Restaurant

@Database(
    version = 35,
    entities = [Item::class, User::class, Restaurant::class, Fetch::class],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RestaurantLocalDatabase : RoomDatabase() {

    abstract fun getItemDao(): IItemDao

    abstract fun getUserDao(): IUserDao

    abstract fun getRestaurantDao(): IRestaurantDao

    abstract fun getFetch(): IFetchDao

    companion object {

        private var _instance: RestaurantLocalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RestaurantLocalDatabase {

            return _instance ?: createDatabaseInstance(context)
        }

        private fun createDatabaseInstance(context: Context): RestaurantLocalDatabase {
            return Room.databaseBuilder(
                context,
                RestaurantLocalDatabase::class.java, "restaurant-database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
                .also { _instance = it }
        }
    }
}