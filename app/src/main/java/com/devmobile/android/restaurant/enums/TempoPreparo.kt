package com.devmobile.android.restaurant.enums

enum class TempoPreparo() {
    LENTO{
        override fun getTimeOfPrepareMinutes(): Int {
            return 20
        }
    },
    NORMAL {
        override fun getTimeOfPrepareMinutes(): Int {
            return 10
        }
    },
    RAPIDO {
        override fun getTimeOfPrepareMinutes(): Int {
            return 5
        }
    };

    abstract fun getTimeOfPrepareMinutes() : Int
}