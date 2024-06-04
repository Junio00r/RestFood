package com.devmobile.android.restaurant.view.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.databinding.LayoutLoadingBinding

class LoadingActivity : Activity() {
    private lateinit var binding: LayoutLoadingBinding

    companion object {
        private var instance: LoadingActivity? = null
        private var intent: Intent? = null

        fun start(context: Context) {

            if (intent == null) {
                intent = Intent(context, LoadingActivity::class.java)
            }
            context.startActivity(intent)
        }

        fun stop() {
            instance?.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}