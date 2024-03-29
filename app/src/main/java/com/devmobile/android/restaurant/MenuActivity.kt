package com.devmobile.android.restaurant

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.core.view.ScrollingView
import com.devmobile.android.restaurant.adapters.FragmentTabAdapter
import com.devmobile.android.restaurant.databinding.ActivityMenuBinding
import com.devmobile.android.restaurant.enums.FoodSection
import com.devmobile.android.restaurant.enums.TempoPreparo
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import com.devmobile.android.restaurant.viewholders.FragmentTabFoodSection
import com.google.android.material.button.MaterialButton
import com.google.android.material.search.SearchView
import com.google.android.material.tabs.TabLayoutMediator


class MenuActivity : AppCompatActivity(), CheckboxClickListener, View.OnClickListener,
    Scrolled {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var searchViewFoods: SearchView
    private lateinit var imageFilterButton: ImageFilterButton
    private lateinit var tabFragmentsInstances: Array<FragmentTabFoodSection>
    private lateinit var floatingButtonCancelFoodOrder: MaterialButton
    private lateinit var floatingButtonPayFoods: MaterialButton
    private lateinit var popupMenu: PopupMenu

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

        addFoodsInDatabase()
        setSearchBarSpecifications()
        setTabLayouts()
        setFilterButton()
        setExtendedFAT()
    }

    // Métodos de inicialização...
    private fun setTabLayouts() {

        val tabsNameId = addTabsName()
        tabFragmentsInstances = addFragmentsTabSection()
        tabFragmentsInstances.forEach {
//            it.addCheckboxClickListener(this)
            it.addScrollListener(this)
        }

        val tabLayout = binding.tabFoodSectionsMenuActivity
        val viewPager2 = binding.pagerFoodSectionsMenuActivity
        val fragmentTabAdapter = FragmentTabAdapter(this, tabFragmentsInstances)

        viewPager2.adapter = fragmentTabAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = getString(tabsNameId[position])
        }.attach()
    }

    private fun addFoodsInDatabase() {

        val foods = ArrayList<Food>()
        val foodDao = RestaurantDatabase.getInstance(this).getFoodDao()

        if (foodDao.getFoodsSize() == 0) {
            foods.addAll(
                listOf(
                    Food(
                        0,
                        "Macarronada",
                        FoodSection.ENTRADA,
                        R.drawable.macarronada,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.LENTO,
                        "Macarronado com Salsicha"
                    ), Food(
                        1,
                        "Hamburger",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        2,
                        "Lasanha",
                        FoodSection.ENTRADA,
                        R.drawable.lasanha,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.LENTO,
                        "Lasanha Irlandesa"
                    ), Food(
                        3,
                        "Feijoada",
                        FoodSection.ENTRADA,
                        R.drawable.feijoada,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.LENTO,
                        "Feijoada Brasileira"
                    ), Food(
                        4,
                        "Camarão",
                        FoodSection.ENTRADA,
                        R.drawable.camarao,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.NORMAL,
                        "Camarao do Mar"
                    ), Food(
                        5,
                        "Queijo",
                        FoodSection.ENTRADA,
                        R.drawable.queijo,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Queijo Fresco"
                    ), Food(
                        6,
                        "Sopa",
                        FoodSection.ENTRADA,
                        R.drawable.sopa,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.NORMAL,
                        "Sopa de Carne"
                    ), Food(
                        7,
                        "Hamburg32er",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        18,
                        "Nusmo",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        9,
                        "Nusmo111",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        10,
                        "Nusmo232",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        11,
                        "Nusmo23",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        12,
                        "Nusmo",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    )
                )
            )

            foodDao.insertAll(foods as List<Food>)
        }
    }

    private fun addTabsName(): Array<Int> {

        return arrayOf(
            R.string.tab_item_entradas,
            R.string.tab_item_pratos_principais,
            R.string.tab_item_bebidas,
            R.string.tab_item_sobremesas,
        )
    }

    private fun addFragmentsTabSection(): Array<FragmentTabFoodSection> {

        return arrayOf(
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.ENTRADA),
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.PRINCIPAL),
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.BEBIDA),
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.SOBREMESA)
        )
    }

    private fun setSearchBarSpecifications() {
        val searchBarFoods = binding.searchBarFoods
        searchViewFoods = binding.searchViewFoods

        searchViewFoods.editText.setOnEditorActionListener { v, actionId, event ->

            searchBarFoods.setText(searchViewFoods.text)
            searchViewFoods.show()
            false
        }

        searchViewFoods.setupWithSearchBar(searchBarFoods)
    }

    private fun setExtendedFAT() {

        floatingButtonCancelFoodOrder = binding.floatingButtonCancelFoodOrder
        floatingButtonPayFoods = binding.floatingButtonPayFoods

        floatingButtonCancelFoodOrder.setOnClickListener(this)
        floatingButtonPayFoods.setOnClickListener(this)
    }

    private fun setFilterButton() {

        imageFilterButton = binding.imageFilterButtonMenuActivity
        imageFilterButton.setOnClickListener(this)

        addMenuOnFilterButton()
    }

    private fun addMenuOnFilterButton() {

        popupMenu = PopupMenu(
            applicationContext, imageFilterButton, Gravity.START, 0, R.style.PopupMenu_View_Local
        )

        popupMenu.menuInflater.inflate(R.menu.searchbar_filter_options, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->

            return@setOnMenuItemClickListener true
        }
    }

    // Metodo de captura de evento de clique...
    override fun onClick(v: View) {

        when (v) {

            floatingButtonCancelFoodOrder -> {

                tabFragmentsInstances.forEach { it.cancelFoodSelected() }
            }

            floatingButtonPayFoods -> {

                Toast.makeText(
                    this,
                    "Seu pedido foi enviado para o balcão do restaurante!",
                    Toast.LENGTH_LONG
                ).show()
            }

            imageFilterButton -> {

                popupMenu.show()
            }
        }
    }

    // Métodos de implementacoes de interfaces criadas
    override fun hasBeenCheckboxChecked(v: FoodCardViewHolder, isCheckboxChecked: Boolean) {

    }

    override fun hasBeenScrolled(data: ScrollingView, dx: Int, dy: Int) {

        if (dy != 0) {

            hideFloatingButtonVertically(data, dy)
        }
    }

    private fun hideFloatingButtonVertically(recyclerViewOfFoodCards: ScrollingView, dy: Int) {

    }
}
