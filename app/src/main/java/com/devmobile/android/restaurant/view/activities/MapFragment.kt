package com.devmobile.android.restaurant.view.activities

import androidx.fragment.app.Fragment
import com.devmobile.android.restaurant.R

class MapFragment private constructor(): Fragment(R.layout.fragment_map_restaurants) {

    companion object {
        const val FRAGMENT_TAG = "Map"
        private var _instance: MapFragment? = null

        @Synchronized
        fun getInstance(): MapFragment {

            return _instance ?: createInstance()
        }

        @Synchronized
        private fun createInstance(): MapFragment {

            return MapFragment().also {
                _instance = it
            }
        }
    }
}