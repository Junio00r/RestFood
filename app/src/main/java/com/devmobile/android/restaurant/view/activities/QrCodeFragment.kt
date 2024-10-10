package com.devmobile.android.restaurant.view.activities

import androidx.fragment.app.Fragment
import com.devmobile.android.restaurant.R

class QrCodeFragment private constructor() : Fragment(R.layout.fragment_qr_code){

    companion object {
        const val FRAGMENT_TAG = "Qr_code"
        private var _instance: QrCodeFragment? = null

        @Synchronized
        fun getInstance(): QrCodeFragment {

            return _instance ?: createInstance()
        }

        @Synchronized
        private fun createInstance(): QrCodeFragment {

            return QrCodeFragment().also {
                _instance = it
            }
        }
    }

}