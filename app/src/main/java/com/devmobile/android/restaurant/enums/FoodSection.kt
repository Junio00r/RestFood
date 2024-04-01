package com.devmobile.android.restaurant.enums

/**
 * Enum class with all foods sections
 */
enum class FoodSection {

    ENTRADA {
        override fun getFoodSectionName(): String = "Entrada"
    },
    PRINCIPAL {
        override fun getFoodSectionName(): String = "Principal"
    },
    BEBIDA {
        override fun getFoodSectionName(): String = "Bebida"
    },
    SOBREMESA {
        override fun getFoodSectionName(): String = "Sobremesa"
    };

    /**
     * @return a string of section name
     */
    abstract fun getFoodSectionName(): String
}
