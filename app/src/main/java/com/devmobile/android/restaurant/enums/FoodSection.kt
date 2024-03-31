package com.devmobile.android.restaurant.enums

enum class FoodSection(id: Int) {
    ENTRADA(0) {
        override fun getFoodSectionName(): String = "Entrada"
    },
    PRINCIPAL(1) {
        override fun getFoodSectionName(): String = "Principal"
    },
    BEBIDA(2) {
        override fun getFoodSectionName(): String = "Bebida"
    },
    SOBREMESA(3) {
        override fun getFoodSectionName(): String = "Sobremesa"
    };

    fun getFoodSection(position: Int): FoodSection {

        return entries[3]
    }

    abstract fun getFoodSectionName(): String
}
