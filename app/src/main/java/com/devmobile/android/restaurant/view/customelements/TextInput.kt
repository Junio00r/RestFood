package com.devmobile.android.restaurant.view.customelements

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.text.InputFilter
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import com.devmobile.android.restaurant.R


class TextInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    companion object {
        private const val SUPER_STATE_KEY = "SUPER_STATE_KEY"
        private const val SPARSE_STATE_KEY = "SPARSE_STATE_KEY"
        private const val DEFAULT_WIDTH = 256
        private const val DEFAULT_HEIGHT = 180
        private val lengthFilter = InputFilter.LengthFilter(50)
    }

    init {

        LayoutInflater.from(context).inflate(R.layout.layout_text_input, this)
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

    override fun setFocusable(focusable: Boolean) {
        super.setFocusable(focusable)

        isFocusableInTouchMode = focusable
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

    private fun ViewGroup.saveChildViewStates(): SparseArray<Parcelable> {
        val childViewStates = SparseArray<Parcelable>()
        children.forEach { child -> child.saveHierarchyState(childViewStates) }
        return childViewStates
    }

    private fun ViewGroup.restoreChildViewStates(childViewStates: SparseArray<Parcelable>) {
        children.forEach { child -> child.restoreHierarchyState(childViewStates) }
    }
}