package com.devmobile.android.restaurant.view.customelements

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import com.google.android.material.textfield.TextInputEditText

@SuppressLint("ViewConstructor", "ResourceAsColor")
class TextInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : TextInputEditText(context, attrs, defStyleAttr) {

    companion object {

        const val DEFAULT_WIDTH = 256
        const val DEFAULT_HEIGHT = 180
        val lengthFilter = InputFilter.LengthFilter(50)
    }

    init {

        textSize = 17F
        isCursorVisible = true
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

    /**
     * Set the max of characters on input
     * @param max number max
     */
    fun maxLength(max: Int) {
        filters = arrayOf(InputFilter.LengthFilter(max))
    }


    override fun setFilters(filters: Array<out InputFilter>?) {

        val currentFilters: Array<out InputFilter> = if (filters.isNullOrEmpty()) {

            arrayOf(*getFilters(), lengthFilter)

        } else {

            filters
        }

        super.setFilters(currentFilters)
    }

    override fun setFocusable(focusable: Boolean) {
        super.setFocusable(focusable)

        isFocusableInTouchMode = focusable
    }
}