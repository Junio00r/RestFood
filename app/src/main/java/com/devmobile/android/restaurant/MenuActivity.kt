package com.devmobile.android.restaurant

import android.os.Bundle
import android.view.View
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

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var customAdapter: FoodCustomAdapter
    private lateinit var recyclerViewFoods: RecyclerView
    private val foodImagesIds = LinkedList<Int>()
    private val foodNames = LinkedList<String>()
    private lateinit var searchBarFoods : SearchBar
    private lateinit var searchViewFoods : SearchView
    private lateinit var imageFilterButton : ImageFilterButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null){
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

//        binding.searchBarFoods.inflateMenu(R.menu.seachbar_filter_options)
//        binding.searchBarFoods.setOnMenuItemClickListener {
//
//            when(it.itemId) {
//                R.id.menu_filter_option1 -> return@setOnMenuItemClickListener true
//                R.id.menu_filter_option2 -> return@setOnMenuItemClickListener true
//                R.id.menu_filter_option3 -> return@setOnMenuItemClickListener true
//
//                else -> {return@setOnMenuItemClickListener false}
//            }
//        }

        searchViewFoods
            .getEditText()
            .setOnEditorActionListener { v, actionId, event ->
                binding.searchBarFoods.setText(binding.searchViewFoods.getText())
                binding.searchViewFoods.hide()
                false
            }
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

    private fun initImageFilterButton() {
        this.imageFilterButton = binding.imageFilterButton

        imageFilterButton.setOnCreateContextMenuListener { menu, v, menuInfo ->

            v.= imageFilterButton
        }

        imageFilterButton.setOnCreateContextMenuListener { menu, v, menuInfo ->
            when(it.itemId) {
                R.id.menu_filter_option1 -> return@setOnCreateContextMenuListener
                R.id.menu_filter_option2 -> return@setOnCreateContextMenuListener
                R.id.menu_filter_option3 -> return@setOnCreateContextMenuListener

                else -> {
                    return@setOnCreateContextMenuListener
                }
            }
        }
    }
}
