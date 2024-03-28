package com.devmobile.android.restaurant

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.ComponentActivity
import com.devmobile.android.restaurant.databinding.ActivityMainBinding

class MainActivity : ComponentActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflating activity_main.xml
        binding = ActivityMainBinding.inflate(this.layoutInflater)

        setContentView(binding.root)

        // Set a listener click on a enter_button
        binding.enterButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {

    }
}
