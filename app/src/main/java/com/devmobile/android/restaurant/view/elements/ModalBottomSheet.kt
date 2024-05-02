package com.devmobile.android.restaurant.view.elements

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import com.devmobile.android.restaurant.viewmodel.IOnSelectFood
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.viewmodel.viewholders.FoodCardViewHolder
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import kotlin.math.absoluteValue

class ModalBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {
    private lateinit var bottomSheetLayoutContainer: ViewGroup
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var bottomSheetLayout: View

    private lateinit var foodCardViewHolder: FoodCardViewHolder
    private lateinit var foodDescription: MaterialTextView
    private lateinit var foodPreferences: TextInputEditText
    private          var foodPrice: Float? = null

    private lateinit var textInputQuantity: TextInputEditText
    private lateinit var decrementFoodQuantityForOrder: MaterialButton
    private lateinit var incrementFoodQuantityForOrder: MaterialButton
    private          var xFoodQuantityForOrder = 1

    private          var hasInputQuantityFocused = false
    private          var onAddedCallbackOfTabSection: IOnSelectFood? = null

    companion object {

        const val TAG = "ModalBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        bottomSheetLayout = inflater.inflate(R.layout.layout_modal_bottomsheet, container, false)
        bottomSheetLayoutContainer =
            bottomSheetLayout.findViewById(R.id.frameBottomSheetFoodViewGroup)

        setBottomSheetBehavior()
        setFoodImage()
        setFoodDescriptions()
        setFoodPreferences()
        foodPrice = foodCardViewHolder.textFoodPrice.text.substring(3).toFloat()

        textInputQuantity = bottomSheetLayoutContainer.findViewById(R.id.textInputFoodQuantityOrder)

        setInputQuantityFocus()
        addKeyboardListener()

        init()

        return bottomSheetLayout
    }

    private fun setBottomSheetBehavior() {

        bottomSheetBehavior = (dialog as BottomSheetDialog).behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setInputQuantityFocus() {

        textInputQuantity.setOnFocusChangeListener { _, hasFocus ->

            hasInputQuantityFocused = hasFocus
        }
    }

    private fun setFoodImage() {

        val imageFood: ShapeableImageView = bottomSheetLayout.findViewById(R.id.imageFood)
        val drawable = foodCardViewHolder.imageFood.drawable

        imageFood.setImageDrawable(drawable)
        imageFood.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    private fun setFoodDescriptions() {
        foodDescription = bottomSheetLayout.findViewById(R.id.textFoodDescriptionBottomSheet)
        foodDescription.text = foodCardViewHolder.textFoodDescriptions
    }

    private fun setFoodPreferences() {
        foodPreferences = bottomSheetLayout.findViewById(R.id.textFoodPreferences)
        foodPreferences.letterSpacing = 0.04f

        foodPreferences.post {
            foodPreferences.filters = arrayOf(InputFilter.LengthFilter(foodPreferences.width / 8))
        }
    }

    private fun addKeyboardListener() {
        val foodDescription: MaterialTextView =
            bottomSheetLayoutContainer.findViewById(R.id.textFoodDescriptionBottomSheet)

        requireActivity().let {
            KeyboardVisibilityEvent.setEventListener(it, object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {

                    if (isOpen) {

                        if (hasInputQuantityFocused) {

                            foodDescription.updateLayoutParams { this.height -= 100 }
                            foodPreferences.updateLayoutParams { this.height = 0 }

                            return
                        }
                    }

                    if (hasInputQuantityFocused) {

                        foodDescription.updateLayoutParams { this.height += 100 }
                        foodPreferences.updateLayoutParams {
                            this.height = foodDescription.height
                        }
                    }
                }
            })
        }
    }

    private fun init() {

        // Init views references
        decrementFoodQuantityForOrder = bottomSheetLayout.findViewById(R.id.buttonDecrementFoodsQuantity)
        incrementFoodQuantityForOrder = bottomSheetLayout.findViewById(R.id.buttonIncrementFoodsQuantity)
        textInputQuantity = bottomSheetLayout.findViewById(R.id.textInputFoodQuantityOrder)

        // Set clickListener
        decrementFoodQuantityForOrder.setOnClickListener(this)
        incrementFoodQuantityForOrder.setOnClickListener(this)

        // Set Initial Value
        textInputQuantity.setText(xFoodQuantityForOrder.toString())
    }

    override fun onClick(button: View) {

        val textInputQuantityValue = textInputQuantity.text.toString().toInt()
        val quantityCanDecremented = textInputQuantityValue - 1 >= 0
        val quantityCanIncremented = textInputQuantityValue + 1 < textInputQuantity.length() * 10

        when (button) {

            decrementFoodQuantityForOrder -> {

                if (quantityCanDecremented) {

                    xFoodQuantityForOrder--
                    textInputQuantity.setText((textInputQuantityValue - 1).toString())
                }

                textInputQuantity.isCursorVisible = false
                foodPreferences.isCursorVisible = false
            }

            incrementFoodQuantityForOrder -> {

                if (quantityCanIncremented) {

                    xFoodQuantityForOrder++
                    textInputQuantity.setText((textInputQuantityValue + 1).toString())
                }

            }
        }
    }

    fun addOnFoodAddedCallback(onAddedCallbackOfTabSection: IOnSelectFood) {

        if (this.onAddedCallbackOfTabSection == null)
            this.onAddedCallbackOfTabSection = onAddedCallbackOfTabSection
    }

    override fun onDestroy() {


        foodPrice?.let {
            onAddedCallbackOfTabSection?.onAddedFood(
                foodCardViewHolder.foodId!!,
                foodCardViewHolder.textFoodName.text.toString(),
                it.absoluteValue,
                null,
                xFoodQuantityForOrder
            )
        }

        super.onDestroy()
    }

    fun addFoodCardViewHolder(v: FoodCardViewHolder) {

        foodCardViewHolder = v
    }
}