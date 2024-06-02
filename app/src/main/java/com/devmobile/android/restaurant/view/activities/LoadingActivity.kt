package com.devmobile.android.restaurant.view.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.devmobile.android.restaurant.databinding.LayoutLoadingBinding

class LoadingActivity : Activity() {

    companion object {
        private lateinit var binding: LayoutLoadingBinding
        private var instance: LoadingActivity? = null

        fun start(context: Context) {
            val intent = Intent(context, LoadingActivity::class.java)
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