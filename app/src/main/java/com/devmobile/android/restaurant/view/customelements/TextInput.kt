package com.devmobile.android.restaurant.view.customelements

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.extensions.maxLength
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


@SuppressLint("MissingInflatedId")
class TextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private val _layoutInflated =
        LayoutInflater.from(context).inflate(R.layout.layout_text_input, this)

    companion object {

        private const val DEFAULT_WIDTH = 256
        private const val DEFAULT_HEIGHT = 296
    }

    init {

        getTextInput().gravity = Gravity.CENTER
        getTextInputEditText().textSize = 17F
        getTextInputEditText().maxLength(50)
    }

    fun getTextInput(): TextInputLayout {
        return _layoutInflated.findViewById(R.id.textInputContainer)
    }

    fun getTextInputEditText(): TextInputEditText {
        return _layoutInflated.findViewById(R.id.textInputEditText)
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

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        // Nothing
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        // Nothing
    }
}