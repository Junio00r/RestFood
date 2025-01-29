package com.devmobile.android.restaurant.view.customelements

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import androidx.databinding.BindingAdapter
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.usecase.maxLength
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


@SuppressLint("MissingInflatedId")
class TextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int? = null,
) : TextInputLayout(context, attrs) {

    private val _layoutInflated = LayoutInflater.from(context).inflate(R.layout.layout_text_input, this)

    val textInputLayout: TextInputLayout = _layoutInflated.findViewById(R.id.textInputContainer)
    val textInputEditText: TextInputEditText = _layoutInflated.findViewById(R.id.textInputEditText)

    init {

        textInputLayout.gravity = Gravity.CENTER
        textInputEditText.textSize = 17F
        textInputEditText.maxLength(50)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Width
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        // Height
        val heightMode = MeasureSpec.getMode(textInputLayout.measuredHeight)
        val heightSize = MeasureSpec.getSize(textInputLayout.measuredHeight)

        val textInputWidth: Int = when (widthMode) {

            MeasureSpec.AT_MOST -> Math.max(widthSize, DEFAULT_WIDTH)

            MeasureSpec.EXACTLY -> widthSize

            MeasureSpec.UNSPECIFIED -> Math.max(heightSize, DEFAULT_WIDTH)

            else -> Math.max(heightSize, DEFAULT_WIDTH)
        }

        val textInputHeight = when (heightMode) {

            MeasureSpec.AT_MOST -> Math.max(heightSize, DEFAULT_HEIGHT)

            MeasureSpec.EXACTLY -> heightSize

            MeasureSpec.UNSPECIFIED -> Math.max(heightSize, DEFAULT_HEIGHT)

            else -> Math.max(heightSize, DEFAULT_HEIGHT)
        }

        setMeasuredDimension(textInputWidth, textInputHeight)
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        // Nothing
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        // Nothing
    }

    companion object {

        private const val DEFAULT_WIDTH = 600
        private const val DEFAULT_HEIGHT = 40

    }

    object Adapters {

        @JvmStatic
        @BindingAdapter("app:isMultiline")
        fun isMultiline(view: TextInput, isMultiline: Boolean) {

            when (isMultiline) {
                true -> {
                    view.textInputEditText.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                }

                false -> {
                    // Nothing
                }
            }
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setText(input: TextInput, text: String?) {
            if (text != input.textInputEditText.text.toString()) {
                input.textInputEditText.setText(text)
            }
        }
    }
}