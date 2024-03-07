package com.devmobile.android.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devmobile.android.restaurant.databinding.ActivityMenuBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.absoluteValue

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    fun initViews() {
        val tabLayout = binding.tabFoodSections
        val viewPager2 = binding.pagerFoodSections
        val adapter = TabViewPagerAdapter(this)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = getString(adapter.tabs[position])
        }.attach()
    }
}
