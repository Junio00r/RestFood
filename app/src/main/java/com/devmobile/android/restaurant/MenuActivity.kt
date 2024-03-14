package com.devmobile.android.restaurant

import android.os.Bundle
import android.view.Gravity
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.databinding.ActivityMenuBinding
import com.devmobile.android.restaurant.recyclerview.FoodCustomAdapter
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.android.material.tabs.TabLayoutMediator
import java.util.LinkedList
import kotlin.math.absoluteValue

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var customAdapter: FoodCustomAdapter
    private lateinit var recyclerViewFoods: RecyclerView
    private val foodImagesIds = LinkedList<Int>()
    private val foodNames = LinkedList<String>()
    private lateinit var searchBarFoods: SearchBar
    private lateinit var searchViewFoods: SearchView
    private lateinit var imageFilterButton: ImageFilterButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            binding = ActivityMenuBinding.inflate(this.layoutInflater)
            setContentView(binding.root)

            init()
        } else {

        }
    }

    fun init() {

        initTabLayoutSpecifications()
        initSearchBarSpecifications()
        putCardViewValues()
        initRecyclerView()
        initImageFilterButton()
    }

    private fun initTabLayoutSpecifications() {
        val tabLayout = binding.tabFoodSections
        val viewPager2 = binding.pagerFoodSections

        val adapter = TabViewPagerAdapter(this)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = getString(adapter.tabs[position])
        }.attach()
    }

    private fun initSearchBarSpecifications() {
        this.searchBarFoods = binding.searchBarFoods
        this.searchViewFoods = binding.searchViewFoods

        searchViewFoods.getEditText().setOnEditorActionListener { v, actionId, event ->
            searchBarFoods.setText(searchViewFoods.text)
            searchViewFoods.hide()
            false
        }

        searchViewFoods.setupWithSearchBar(searchBarFoods)
    }

    private fun initRecyclerView() {

        recyclerViewFoods = binding.recyclerFoods
        customAdapter = FoodCustomAdapter(foodImagesIds, foodNames, this)
        recyclerViewFoods.adapter = customAdapter
        recyclerViewFoods.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    }

    private fun putCardViewValues() {

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
                "Macarronada", "Hamburguer", "Principal", "Feijoada", "Camarão", "Queijo", "Sopa"
            )
        )
    }

    private fun initImageFilterButton() {

        this.imageFilterButton = binding.imageFilterButton
        val popupMenu = PopupMenu(
            applicationContext,
            imageFilterButton,
            Gravity.START,
            0,
            R.style.PopupMenu_View_Local
        )

        popupMenu.menuInflater.inflate(R.menu.seachbar_filter_options, popupMenu.menu)

        imageFilterButton.setOnClickListener {

            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { menuItem ->

                return@setOnMenuItemClickListener true
            }

        }
    }
}
