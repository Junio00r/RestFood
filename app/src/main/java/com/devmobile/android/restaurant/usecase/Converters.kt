package com.devmobile.android.restaurant.usecase

import androidx.room.TypeConverter
import com.google.gson.Gson

object Converters {

    @TypeConverter
    fun fromListToString(list: List<String>): String {

        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(value: String): ArrayList<String> {

        return Gson().fromJson<ArrayList<String>>(value, ArrayList::class.java)
    }
}