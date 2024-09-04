package com.devmobile.android.restaurant

import java.text.NumberFormat

class ZoneNumberFormat {

    companion object {

        fun format(numberToFormat: Number): String {
            val numberFormatMask = NumberFormat.getInstance()
            numberFormatMask.minimumFractionDigits = 2
            numberFormatMask.isGroupingUsed = true

            return numberFormatMask.format(numberToFormat)
        }

        /**
         * @param numberToFormat number to set zone decimal mask.
         * @return **String** of number formatted with zone decimal mask or null otherwise.
         * @exception NumberFormatException case does numberToFormat can't be
         * converted on float number.
         */
        fun format(numberToFormat: String): String? {

            try {

                return format(numberToFormat.toFloat())

            } catch (e: NumberFormatException) {

                println("Number to format isn't compatible type!")
                e.printStackTrace()
            }

            return null
        }
    }
}