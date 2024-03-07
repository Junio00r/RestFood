package com.devmobile.android.restaurant

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.compose.ui.layout.Layout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable

data class FilterItem(
    var text: String,
    var iconSize: Float,
    @DrawableRes val leftChipIcon: Int? = null,
    @DrawableRes val rightChipIcon: Int? = null
)

// Extension, crio uma função separa onde pode ser acessado
// por qualquer instancia da classe FilterItem
fun FilterItem.toChip(context: Context): Chip {

    val chip = if (rightChipIcon == null) {
        LayoutInflater.from(context).inflate(R.layout.layout_chip, null, false) as Chip
    } else {
        Chip(ContextThemeWrapper(
            context, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice
        ))
    }

    setChipAtributes(chip)

    return chip
}

private fun FilterItem.setChipAtributes(chip: Chip) {
    chip.text = this.text
    chip.chipStrokeWidth = 2f

    if (chip.closeIcon != null) {
        chip.setChipBackgroundColorResource(R.color.thirdary_color)
    }

    // Set a stroke color
    // Defino uma cor para borda
    chip.setChipStrokeColorResource(R.color.fourty_color)

    leftChipIcon?.let {
        chip.chipIconSize = this.iconSize
        chip.setChipIconResource(it)
        chip.chipStartPadding = 20f
    }
    rightChipIcon?.let {
        chip.setCloseIconResource(it)
        chip.isCloseIconVisible = true
    }
}



