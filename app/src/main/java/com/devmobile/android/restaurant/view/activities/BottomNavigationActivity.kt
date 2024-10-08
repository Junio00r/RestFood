package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.ActivityHomeBinding

class BottomNavigationActivity : AppCompatActivity() {

    //    private val repository =
//    private val _viewModel  =
    private val _binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(_binding.root)
        subscribeObservers()
    }

    private fun drawViews() {

    }

    private fun subscribeObservers() {

        _binding.bottomNavigationView.setOnItemReselectedListener { item ->

            when(item.itemId) {
                R.id.menu_screen_1 -> {
                    Log.i("HOME_SCREEN", "Menu item 1\n")
                }
                R.id.menu_screen_2 -> {
                    Log.i("HOME_SCREEN", "Menu item 2\n")
                }
                R.id.menu_screen_3 -> {
                    Log.i("HOME_SCREEN", "Menu item 3\n")
                }
                R.id.menu_screen_4 -> {
                    Log.i("HOME_SCREEN", "Menu item 4\n")
                }
            }
        }
    }
}