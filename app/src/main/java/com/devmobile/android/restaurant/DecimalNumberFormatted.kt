package com.devmobile.android.restaurant

import java.text.NumberFormat

class DecimalNumberFormatted {
    companion object {

        fun format(numberToFormat: Float): String {

            return format(numberToFormat.toString())
        }

        fun format(numberToFormat: String): String {
            val numberFormatted = NumberFormat.getInstance()
            numberFormatted.minimumFractionDigits = 2

            return numberFormatted.format(numberToFormat.toFloat())
        }
    }
}