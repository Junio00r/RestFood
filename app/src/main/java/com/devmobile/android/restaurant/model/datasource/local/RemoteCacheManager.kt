package com.devmobile.android.restaurant.model.datasource.local

/**
 * Store data fetch by web.
 */
object RemoteCacheManager {

    private const val MAX_SIZE = 1000
    private val _data = HashMap<String, Any>()

    fun put(key: String, value: Any): Boolean {

        if (_data.size >= MAX_SIZE) return false

        _data[key] = value
        return true
    }

    fun remove(key: String) {
        _data.remove(key)
    }

    fun get(key: String): Any? {

        if (_data[key] == null) return null

        return _data[key]
    }

    fun cacheSize(): Int {
        return _data.size
    }
}