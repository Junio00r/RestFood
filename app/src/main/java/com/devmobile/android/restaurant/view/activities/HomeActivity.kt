package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.ActivityRestaurantChoiceBinding

class RestaurantChoiceActivity : AppCompatActivity() {

    //    private val repository =
//    private val _viewModel  =
    private val _binding: ActivityRestaurantChoiceBinding by lazy {
        ActivityRestaurantChoiceBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(_binding.root)
    }

    private fun drawingView() {

    }

    private fun subscribeObservers() {

        _binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {

                R.id.menu_item_1 -> {

                }
                R.id.menu_item_2 -> {

                }
                R.id.menu_item_3 -> {

                }
                R.id.menu_item_4 -> {

                }
                else -> {}
            }
        }
    }
}