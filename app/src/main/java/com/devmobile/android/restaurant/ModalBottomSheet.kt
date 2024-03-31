package com.devmobile.android.restaurant

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
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
    private lateinit var bottomSheetLayout: View
    private lateinit var bottomSheetLayoutContainer: ViewGroup
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private lateinit var foodCardViewHolder: FoodCardViewHolder
    private lateinit var foodPreferences: TextInputEditText
    private lateinit var textInputQuantity: TextInputEditText
    private var hasInputQuantityFocused = false
    private lateinit var buttonDecrementFood: MaterialButton
    private lateinit var buttonIncrementFood: MaterialButton
    private var quantityOfFoods = 1
    private var onAddedCallbackOfTabSection: FoodSelectedCallback? = null
    private var foodPrice: Float? = null

    companion object {

        const val TAG = "ModalBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        bottomSheetLayout = inflater.inflate(R.layout.modal_bottomsheet_layout, container, false)
        bottomSheetLayoutContainer =
            bottomSheetLayout.findViewById(R.id.frameBottomSheetFoodViewGroup)

        setBottomSheetBehavior()
        setFoodImage()
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
        buttonDecrementFood = bottomSheetLayout.findViewById(R.id.buttonDecrementFoodsQuantity)
        buttonIncrementFood = bottomSheetLayout.findViewById(R.id.buttonIncrementFoodsQuantity)
        textInputQuantity = bottomSheetLayout.findViewById(R.id.textInputFoodQuantityOrder)

        // Set clickListener
        buttonDecrementFood.setOnClickListener(this)
        buttonIncrementFood.setOnClickListener(this)

        // Set Initial Value
        textInputQuantity.setText(quantityOfFoods.toString())
    }

    override fun onClick(button: View) {

        val quantityCanDecremented = getTextInputValue() - 1 >= 0
        val quantityCanIncremented = getTextInputValue() + 1 < textInputQuantity.length() * 10

        when (button) {

            buttonDecrementFood -> {

                if (quantityCanDecremented) {

                    quantityOfFoods--
                    textInputQuantity.setText((getTextInputValue() - 1).toString())
                }

                textInputQuantity.isCursorVisible = false
                foodPreferences.isCursorVisible = false
            }

            buttonIncrementFood -> {

                if (quantityCanIncremented) {

                    quantityOfFoods++
                    textInputQuantity.setText((getTextInputValue() + 1).toString())
                }

                textInputQuantity.isCursorVisible = false
                foodPreferences.isCursorVisible = false
            }
        }
    }

    fun addOnFoodAddedCallback(onAddedCallbackOfTabSection: FoodSelectedCallback) {

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
                quantityOfFoods
            )
        }

        super.onDestroy()
    }

    private fun getTextInputValue(): Int {

        return textInputQuantity.text.toString().toInt()
    }

    fun setBottomSheetAttributes(v: FoodCardViewHolder) {

        foodCardViewHolder = v
    }
}