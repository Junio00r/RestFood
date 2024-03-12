package com.devmobile.android.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.databinding.ActivityMenuBinding
import com.devmobile.android.restaurant.recyclerview.FoodCustomAdapter
import com.google.android.material.tabs.TabLayoutMediator
import java.util.LinkedList

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var customAdapter: FoodCustomAdapter
    private lateinit var recyclerViewFoods: RecyclerView
    private val foodImagesIds = LinkedList<Int>()
    private val foodNames = LinkedList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        initViews()
        setCardViewValues()
        initRecyclerView()
    }

    fun initViews() {
        val tabLayout = binding.tabFoodSections
        val viewPager2 = binding.pagerFoodSections
        recyclerViewFoods = binding.recyclerFoods
        val adapter = TabViewPagerAdapter(this)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = getString(adapter.tabs[position])
        }.attach()
    }

    private fun initRecyclerView() {

        customAdapter = FoodCustomAdapter(foodImagesIds, foodNames, this)
        recyclerViewFoods.adapter = customAdapter
        recyclerViewFoods.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    }

    private fun setCardViewValues() {
        foodImagesIds.addAll(
            listOf(
                R.drawable.macarronada,
                R.drawable.hamburguer,
                R.drawable.principal,
                R.drawable.feijoada,
                R.drawable.camarao,
                R.drawable.queijo,
                R.drawable.sopa
            )
        )

        foodNames.addAll(
            listOf(
                "Macarronada",
                "Hamburguer",
                "Principal",
                "Feijoada",
                "Camarão",
                "Queijo",
                "Sopa"
            )
        )
    }
}
