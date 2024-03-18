package com.devmobile.android.restaurant

import android.os.Bundle
import android.view.Gravity
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.adapters.FoodCardAdapter
import com.devmobile.android.restaurant.adapters.FragmentTabAdapter
import com.devmobile.android.restaurant.dao.FoodDao
import com.devmobile.android.restaurant.databinding.ActivityMenuBinding
import com.devmobile.android.restaurant.enums.FoodSection
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.android.material.tabs.TabLayoutMediator

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var customAdapter: FoodCardAdapter
    private lateinit var recyclerViewFoods: RecyclerView
    private lateinit var searchBarFoods: SearchBar
    private lateinit var searchViewFoods: SearchView
    private lateinit var imageFilterButton: ImageFilterButton

    // Fragment Tab Attributes
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
        initTabLayoutSpecifications()
        initImageFilterButton()
    }

    private fun initTabLayoutSpecifications() {

        val tabsNameId = arrayOf(
            R.string.tab_item_entradas,
            R.string.tab_item_pratos_principais,
            R.string.tab_item_bebidas,
            R.string.tab_item_sobremesas,
            R.string.tab_todos_itens
        )
        val tabFragmentsInstances = arrayOf(
            FragmentTabFoodSection(),
            FragmentTabFoodSection(),
            FragmentTabFoodSection(),
            FragmentTabFoodSection(),
            FragmentTabFoodSection()
        )
        val foods = ArrayList<Food>()
        val foodDao = RestaurantDatabase.getInstance(this).getFoodDao()
        foods.addAll(
            listOf(
                Food(0, "Macarronada", FoodSection.ENTRADA, R.drawable.macarronada, R.drawable.stopwatch_67, "Macarronado com Salsicha"
                ), Food(
                    1, "Hamburger", FoodSection.ENTRADA, R.drawable.hamburguer, R.drawable.stopwatch_67, "Big Hamburger"
                ), Food(
                    2, "Lasanha", FoodSection.ENTRADA, R.drawable.lasanha, R.drawable.stopwatch_67, "Lasanha Irlandesa"
                ), Food(
                    3, "Feijoada", FoodSection.ENTRADA, R.drawable.feijoada, R.drawable.stopwatch_67, "Feijoada Brasileira"
                ), Food(
                    4, "Camarão", FoodSection.TODAS, R.drawable.camarao, R.drawable.stopwatch_67, "Camarao do Mar"
                ), Food(
                    5, "Queijo", FoodSection.TODAS, R.drawable.queijo, R.drawable.stopwatch_67, "Queijo Fresco"
                ), Food(
                    6, "Sopa", FoodSection.TODAS, R.drawable.sopa, R.drawable.stopwatch_67, "Sopa de Carne"
                )
            )
        )
        foodDao.insertAll(foods as List<Food>)
        val tabLayout = binding.tabFoodSections
        val viewPager2 = binding.pagerFoodSections
        val fragmentTabAdapter = FragmentTabAdapter(this, baseContext, tabsNameId, tabFragmentsInstances)

        viewPager2.adapter = fragmentTabAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = getString(fragmentTabAdapter.tabsNameId[position])
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

    private fun initImageFilterButton() {

        this.imageFilterButton = binding.imageFilterButton
        val popupMenu = PopupMenu(
            applicationContext, imageFilterButton, Gravity.START, 0, R.style.PopupMenu_View_Local
        )

        popupMenu.menuInflater.inflate(R.menu.searchbar_filter_options, popupMenu.menu)

        imageFilterButton.setOnClickListener {

            popupMenu.show()

            popupMenu.setOnMenuItemClickListener { menuItem ->

                return@setOnMenuItemClickListener true
            }

        }
    }
}
