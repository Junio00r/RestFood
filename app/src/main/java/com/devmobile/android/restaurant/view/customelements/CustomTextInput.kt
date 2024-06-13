package com.devmobile.android.restaurant.view.customelements

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import com.google.android.material.textfield.TextInputEditText

@SuppressLint("ViewConstructor", "ResourceAsColor")
class CustomTextInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {

    companion object {

        const val FILTER_MAX_LENGTH = 30
        const val HORIZONTAL_PADDING = 14
        const val DEFAULT_WIDTH = 256
        const val DEFAULT_HEIGHT = 180

    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {

        changedView.isEnabled = visibility != INVISIBLE or INVISIBLE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Width
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        // Height
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val textInputWidth: Int = when (widthMode) {

            MeasureSpec.AT_MOST -> Math.min(widthSize, DEFAULT_WIDTH)

            MeasureSpec.EXACTLY -> widthSize

            else -> DEFAULT_WIDTH
        }

        val textInputHeight = when (heightMode) {

            MeasureSpec.AT_MOST -> Math.min(heightSize, DEFAULT_HEIGHT)

            MeasureSpec.EXACTLY -> heightSize

            else -> DEFAULT_HEIGHT
        }

        setMeasuredDimension(textInputWidth, textInputHeight)
    }

    override fun setFilters(filters: Array<out InputFilter>?) {

        val currentFilters = getFilters()

        val myFilters: Array<out InputFilter> =
            arrayOf(*currentFilters, InputFilter.LengthFilter(FILTER_MAX_LENGTH))

        super.setFilters(myFilters)
    }
}