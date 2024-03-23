package com.devmobile.android.restaurant

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText


class ModalBottomSheet : BottomSheetDialogFragment(), View.OnClickListener,
    BottomSheetNotification {
    private lateinit var viewInflate: View
    private lateinit var inputQuantity: TextInputEditText
    private lateinit var foodCard: FoodCardViewHolder
    lateinit var bottomSheetHidedNotification: BottomSheetNotification

    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        viewInflate = inflater.inflate(R.layout.modal_bottomsheet_layout, container, false)

        val bottomSheetBehavior = (dialog as BottomSheetDialog).behavior
        val relativeLayout: ViewGroup =
            viewInflate.findViewById(R.id.frameBottomSheetFoodSelectedBottomSheet)
        bottomSheetBehavior.peekHeight = relativeLayout.resources.displayMetrics.heightPixels

        // AddedImageFood
        var foodView: ShapeableImageView = viewInflate.findViewById(R.id.imageFoodBottomSheet)
        val drawable = foodCard.imageFood.drawable
        foodView.setImageDrawable(drawable)
        foodView.scaleType = ImageView.ScaleType.FIT_XY

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> bottomSheetHidedNotification
                }
            }

            override fun onSlide(p0: View, p1: Float) { }
        })

        init()
        return viewInflate
    }

    companion object {
        const val TAG = "ModalBottomSheet"
        private const val INITIAL_COUNT = 1
    }

    override fun onClick(v: View) {

        if (v.id == R.id.buttonDecrementQuantityBottomSheet) {

            if (getEdittextFoodQuantity() - 1 >= 0) {

                inputQuantity.setText((getEdittextFoodQuantity() - 1).toString())
            }

        } else if (v.id == R.id.buttonIncrementQuantityBottomSheet) {

            if ((getEdittextFoodQuantity() + 1).toString()
                    .count() <= inputQuantity.getMaxLength()
            ) {

                inputQuantity.setText((getEdittextFoodQuantity() + 1).toString())
            }
        }
    }

    private fun init() {
        viewInflate.findViewById<Button>(R.id.buttonDecrementQuantityBottomSheet)
            .setOnClickListener(this)
        viewInflate.findViewById<Button>(R.id.buttonIncrementQuantityBottomSheet)
            .setOnClickListener(this)


        inputQuantity = viewInflate.findViewById(R.id.edittextFoodQuantityPedidoBottomSheet)
        inputQuantity.setText(INITIAL_COUNT.toString())
    }

    private fun getEdittextFoodQuantity(): Int {

        return viewInflate.findViewById<TextInputEditText>(R.id.edittextFoodQuantityPedidoBottomSheet).text.toString()
            .toInt()
    }

    private fun TextInputEditText.getMaxLength(): Int {
        filters.forEach {
            if (it is InputFilter.LengthFilter) {
                return it.max
            }
        }

        return -1
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        bottomSheetHidedNotification
    }

    fun setBottomSheetAtributes(v: FoodCardViewHolder) {
        foodCard = v
    }

    fun setBottomSheetHideNotification(bottomSheetHidedNotificationReceived: BottomSheetNotification) {
        this.bottomSheetHidedNotification = bottomSheetHidedNotificationReceived
    }

    override fun bottomSheetHidedNotification() {
        bottomSheetHidedNotification.bottomSheetHidedNotification()
    }
}