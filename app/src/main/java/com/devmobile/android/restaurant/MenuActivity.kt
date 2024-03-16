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

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var customAdapter: FoodCustomAdapter
    private lateinit var recyclerViewFoods: RecyclerView
    private val foods = LinkedList<Food>()
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

    private fun init() {

        initSearchBarSpecifications()
        setFoods()
        initTabLayoutSpecifications()
        initRecyclerView()
        initImageFilterButton()
    }

    private fun initTabLayoutSpecifications() {

        val tabLayout = binding.tabFoodSections
        val viewPager2 = binding.pagerFoodSections
        val fragmentTabAdapter = FragmentTabAdapter(this)
        viewPager2.adapter = fragmentTabAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = getString(fragmentTabAdapter.tabs[position])
        }.attach()
    }

    private fun initSearchBarSpecifications() {
        this.searchBarFoods = binding.searchBarFoods
        this.searchViewFoods = binding.searchViewFoods

        searchViewFoods.editText.setOnEditorActionListener { v, actionId, event ->
            searchBarFoods.setText(searchViewFoods.text)
            searchViewFoods.hide()
            false
        }

        searchViewFoods.setupWithSearchBar(searchBarFoods)
    }

    private fun initRecyclerView() {

        this.recyclerViewFoods = binding.recyclerFoods
        customAdapter = FoodCustomAdapter(foods, this)
        recyclerViewFoods.adapter = customAdapter
        recyclerViewFoods.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    }

    private fun setFoods() {

        foods.addAll(
            listOf(
                Food(
                    "Macarronada",
                    FoodSection.ENTRADA,
                    R.drawable.macarronada,
                    "Macarronado com Salsicha"
                ),
                Food(
                    "Hamburger",
                    FoodSection.ENTRADA,
                    R.drawable.hamburguer,
                    "Big Hamburger"
                ),
                Food(
                    "Lasanha",
                    FoodSection.ENTRADA,
                    R.drawable.lasanha,
                    "Lasanha Irlandesa"
                ),
                Food(
                    "Feijoada",
                    FoodSection.ENTRADA,
                    R.drawable.feijoada,
                    "Feijoada Brasileira"
                )
                ,
                Food(
                    "Camarão",
                    FoodSection.ENTRADA,
                    R.drawable.camarao,
                    "Camarao do Mar"
                ),
                Food(
                    "Queijo",
                    FoodSection.ENTRADA,
                    R.drawable.queijo,
                    "Queijo Fresco"
                ),
                Food(
                    "Sopa",
                    FoodSection.ENTRADA,
                    R.drawable.sopa,
                    "Sopa de Carne"
                )
            )
        )
    }

    private fun initImageFilterButton() {

        this.imageFilterButton = binding.imageFilterButton
        val popupMenu = PopupMenu(
            applicationContext, imageFilterButton, Gravity.START, 0, R.style.PopupMenu_View_Local
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
