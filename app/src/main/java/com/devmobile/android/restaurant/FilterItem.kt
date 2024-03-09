package com.devmobile.android.restaurant
//
//import android.content.Context
//import android.view.ContextThemeWrapper
//import android.view.LayoutInflater
//import androidx.annotation.DrawableRes
//import com.google.android.material.chip.Chip
//
//data class FilterItem(
//    var text: String,
//    var iconSize: Float,
//    @DrawableRes val leftChipIcon: Int? = null,
//    @DrawableRes val rightChipIcon: Int? = null
//)
//
//// Extension, crio uma função separa onde pode ser acessado
//// por qualquer instancia da classe FilterItem
//fun FilterItem.toChip(context: Context): Chip {
//
//    val chip = if (rightChipIcon == null) {
//        LayoutInflater.from(context).inflate(R.layout.chip_filter_choice, null, false) as Chip
//    } else {
//        Chip(
//            ContextThemeWrapper(
//                context, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Filter
//            )
//        )
//    }
//
//    setChipAtributes(chip)
//
//    return chip
//}
//
//private fun FilterItem.setChipAtributes(chip: Chip) {
//    chip.text = this.text
//    chip.chipStrokeWidth = 2f
//    chip.setChipStrokeColorResource(R.color.six_color)
//    chip.chipIconSize = 64f
//
//
//    leftChipIcon?.let {
//        chip.chipIconSize = iconSize
//        chip.isCheckable = false
//        chip.chipStartPadding = 20f
//    }
//
//    rightChipIcon?.let {
//        chip.setChipBackgroundColorResource(R.color.thirdary_color)
//        chip.setCloseIconResource(it)
//        chip.isCloseIconVisible = true
//    }
//}
//
//
//
