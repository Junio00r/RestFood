package com.devmobile.android.restaurant.model.enums

/**
 * Enum class with all times to prepare a food
 * @property LENTO when is wait 20 minutes or more for food is ready
 * @property NORMAL wait 10 minutes for food is ready
 * @property RAPIDO wait 5 minutes for food is ready
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