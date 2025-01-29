package com.devmobile.android.restaurant.view.customelements

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.viewbinding.ViewBinding
import com.devmobile.android.restaurant.databinding.LayoutChildRequireSideBinding
import com.devmobile.android.restaurant.databinding.LayoutGroupRequiredSidesBinding

@SuppressLint("ClickableViewAccessibility", "ViewConstructor")
class CustomItemView(val viewType: Int, context: Context) : FrameLayout(context) {
    val binding: ViewBinding by lazy {
        if (viewType == ITEM_TYPE) {

            LayoutChildRequireSideBinding.inflate(LayoutInflater.from(context), this, true)

        } else {

            LayoutGroupRequiredSidesBinding.inflate(LayoutInflater.from(context), this, true)
        }
    }

    var currentItemState: Int = VIEW_NOT_SELECTED_YET
        private set
    var endBinding: ViewBinding? = null
        private set

    init {
        if (viewType == ITEM_TYPE) {

            with((binding as LayoutChildRequireSideBinding).textName) {
                setOnTouchListener { _, event ->
                    event.offsetLocation(left.toFloat(), top.toFloat())
                    binding.root.onTouchEvent(event)
                    true
                }
            }

        }
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    }

    fun setImage(startImage: Int) {

        if (viewType == ITEM_TYPE) {

            (binding as LayoutChildRequireSideBinding).imageRequiredSide.setImageResource(startImage)

        } else {

            throw Exception("Item type no support image.")
        }
    }

    fun setItemName(itemName: String) {

        if (viewType == ITEM_TYPE) {

            (binding as LayoutChildRequireSideBinding).textName.text = itemName

        } else {

            (binding as LayoutGroupRequiredSidesBinding).textGroupName.text = itemName
        }
    }

    @SuppressLint("ResourceType")
    fun addEndView(viewBinding: ViewBinding) {

        if (viewType == ITEM_TYPE) {

            if ((binding as LayoutChildRequireSideBinding).layoutEnd.children.elementAtOrNull(0) == null) {
                (binding as LayoutChildRequireSideBinding).layoutEnd.addView(viewBinding.root)
                endBinding = viewBinding
            }else{
                (binding as LayoutChildRequireSideBinding).layoutEnd.children.first().visibility = View.VISIBLE
            }

        } else {

            (binding as LayoutGroupRequiredSidesBinding).imageEnd.visibility = View.GONE
        }
        currentItemState = VIEW_ALREADY_SELECTED
    }

    fun removeView() {

        if (viewType == ITEM_TYPE) {

            (binding as LayoutChildRequireSideBinding).layoutEnd.children.first().visibility = View.GONE

        } else {

            (binding as LayoutGroupRequiredSidesBinding).imageEnd.visibility = View.GONE
        }
        currentItemState = VIEW_NOT_SELECTED_YET
    }

    fun onItemClick(listener: OnClickListener) {
        binding.root.setOnClickListener(listener)
    }

    companion object {
        const val UNSPECIFIED = 1
        const val ITEM_TYPE = 2

        const val VIEW_NOT_SELECTED_YET = 0
        const val VIEW_ALREADY_SELECTED = 1
    }
}