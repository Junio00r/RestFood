package com.devmobile.android.restaurant.usecase

import androidx.room.TypeConverter
import com.google.gson.Gson

object Converters {

    @TypeConverter
    fun fromListToString(list: List<String>): String {

        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(value: String): List<String> {

        return Gson().fromJson<List<String>>(value, List::class.java)
    }
}