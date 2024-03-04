package com.devmobile.android.restaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.devmobile.android.restaurant.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)

        setContentView(binding.root)
    }
}
