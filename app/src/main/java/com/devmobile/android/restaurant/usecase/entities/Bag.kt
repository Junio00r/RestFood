package com.devmobile.android.restaurant.usecase.entities

import com.devmobile.android.restaurant.model.datasource.local.entities.Item

class Bag private constructor() {
    private val items: MutableList<Item> = mutableListOf()

    companion object {
        private var instance: Bag? = null

        @JvmStatic
        fun getInstance(): Bag {
            return instance ?: createInstance()
        }

        private fun createInstance(): Bag {
            return synchronized(this) {
                instance ?: Bag().also {
                    instance = it
                }
            }
        }
    }

    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    fun addItem(item: Item): Boolean {
        return items.add(item)
    }

    fun removeItem(item: Item): Boolean {
        return items.remove(item)
    }

    fun updateItem(itemId: Long, newItem: Item): Boolean {
        val itemIndex: Int = items.indexOfFirst { it.id == itemId }

        if (itemIndex >= 0) {
            items[itemIndex] = newItem
            return true
        }

        return false
    }

    fun itemAlreadyExists(item: Item): Boolean {
        return items.contains(item)
    }

    fun getItems(): List<Item> {
        return items
    }
}
