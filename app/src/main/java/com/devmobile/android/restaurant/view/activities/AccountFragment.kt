package com.devmobile.android.restaurant.view.activities

import androidx.fragment.app.Fragment
import com.devmobile.android.restaurant.R

class AccountFragment private constructor() : Fragment(R.layout.fragment_accounts) {

    companion object {
        const val FRAGMENT_TAG = "Account"
        private var _instance: AccountFragment? = null

        @Synchronized
        fun getInstance(): AccountFragment {

            return _instance ?: createInstance()
        }

        @Synchronized
        private fun createInstance(): AccountFragment {

            return AccountFragment().also {
                _instance = it
            }
        }
    }
}