package com.devmobile.android.restaurant.enums

/**
 * Enum class with all time to prepare a food
 */
enum class TempoPreparo() {
    LENTO {
        override fun getTimeOfPrepareMinutes(): Int = 20
    },
    NORMAL {
        override fun getTimeOfPrepareMinutes(): Int = 10
    },
    RAPIDO {
        override fun getTimeOfPrepareMinutes(): Int = 5
    };

    /**
     * @return time to prepare a food
     */
    abstract fun getTimeOfPrepareMinutes(): Int
}