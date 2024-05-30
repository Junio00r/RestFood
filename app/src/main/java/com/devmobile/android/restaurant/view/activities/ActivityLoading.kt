package com.devmobile.android.restaurant.view.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devmobile.android.restaurant.R

class ActivityLoading : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutInflater.inflate(R.layout.activity_loading, null)
        setContentView(R.layout.activity_loading)
    }
}