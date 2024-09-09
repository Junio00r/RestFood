package com.devmobile.android.restaurant.view.customelements

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.extensions.maxLength
import com.devmobile.android.restaurant.extensions.restoreChildViewStates
import com.devmobile.android.restaurant.extensions.saveChildViewStates
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

        private const val SUPER_STATE_KEY = "SUPER_STATE_KEY"
        private const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"
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

    override fun updateViewLayout(view: View?, params: ViewGroup.LayoutParams?) {
        super.updateViewLayout(view, params)
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putParcelable(SUPER_STATE_KEY, super.onSaveInstanceState())
            putSparseParcelableArray(SPARSE_STATE_KEY, saveChildViewStates())
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var newState = state
        if (newState is Bundle) {
            val childrenState = newState.getSparseParcelableArray<Parcelable>(SPARSE_STATE_KEY)
            childrenState?.let { restoreChildViewStates(it) }
            newState = newState.getParcelable(SUPER_STATE_KEY)
        }
        super.onRestoreInstanceState(newState)
    }
}