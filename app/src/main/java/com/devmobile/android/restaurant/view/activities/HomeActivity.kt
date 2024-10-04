package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    //    private val repository =
//    private val _viewModel  =
    private val _binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(_binding.root)
    }

    private fun drawingView() {

    }

    private fun subscribeObservers() {

        _binding.bottomNavigationView.setOnItemReselectedListener { item ->

            when(item.itemId) {
                R.id.menu_item_1 -> {
                    Log.i("HOME_SCREEN", "Menu item 1\n\n\n")
                }
                R.id.menu_item_2 -> {
                    Log.i("HOME_SCREEN", "Menu item 2\n\n\n")
                }
                R.id.menu_item_3 -> {
                    Log.i("HOME_SCREEN", "Menu item 3\n\n\n")
                }
                R.id.menu_item_4 -> {
                    Log.i("HOME_SCREEN", "Menu item 4\n\n\n")
                }
            }
        }
    }
}