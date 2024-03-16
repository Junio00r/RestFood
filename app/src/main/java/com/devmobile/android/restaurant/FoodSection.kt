package com.devmobile.android.restaurant

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
    },
    TODAS {
        override fun getFoodSectionName(): String = "Todas"
    };

    abstract fun getFoodSectionName() : String
}
