package com.devmobile.android.restaurant.usecase.enums

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
     * @return section name
     */
    abstract fun getFoodSectionName(): String
}
