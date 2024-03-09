package com.devmobile.android.restaurant

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip

@SuppressLint("ViewConstructor")
class CustomChipFilter(
    private val context: Context,
    private var textChip: String,
    private var iconSize: Float? = 64f,
    @DrawableRes leftChipIcon: Int? = null,
    @DrawableRes rightChipIcon: Int? = null,
) : Chip(context) {

    init {

        setCheckedIconResource(R.drawable.ic_check_chip_filter)
        setChipBackgroundColorResource(R.color.bg_chip)
        setChipStrokeColorResource(R.color.six_color)

        chipIconSize = this.iconSize!!
        text = this.textChip
        chipStrokeWidth = 2f
        isCheckable = true

        leftChipIcon?.let {

            isVisible = true
        }

        rightChipIcon?.let {

            isCloseIconVisible = true
            setCloseIconResource(it)
            isCheckable = false
        }
    }
}
