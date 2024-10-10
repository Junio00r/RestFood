package com.devmobile.android.restaurant.view.activities

import androidx.fragment.app.Fragment
import com.devmobile.android.restaurant.R

class HomeFragment private constructor() : Fragment(R.layout.fragment_restaurant_home) {

    companion object {
        const val FRAGMENT_TAG = "Home"
        private var _instance: HomeFragment? = null

        @Synchronized
        fun getInstance(): HomeFragment {

            return _instance ?: createInstance()
        }

        @Synchronized
        private fun createInstance(): HomeFragment {

            return HomeFragment().also {
                _instance = it
            }
        }
    }
}