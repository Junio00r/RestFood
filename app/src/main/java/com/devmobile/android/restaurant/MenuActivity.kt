package com.devmobile.android.restaurant

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.core.view.ScrollingView
import androidx.core.view.doOnLayout
import androidx.core.view.marginBottom
import com.devmobile.android.restaurant.adapters.FragmentTabAdapter
import com.devmobile.android.restaurant.databinding.ActivityMenuBinding
import com.devmobile.android.restaurant.enums.FoodSection
import com.devmobile.android.restaurant.enums.TempoPreparo
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import com.devmobile.android.restaurant.viewholders.FragmentTabFoodSection
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.String
import kotlin.math.abs
import kotlin.math.absoluteValue


class MenuActivity : AppCompatActivity(), ClickNotification, View.OnClickListener, Scrolled {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var searchBarFoods: SearchBar
    private lateinit var searchViewFoods: SearchView
    private lateinit var imageFilterButton: ImageFilterButton
    private lateinit var tabFragmentsInstances: Array<FragmentTabFoodSection>
    private var floatingButtonStillCanScrolledToDown = true
    private var floatingButtonStillCanScrolledToUp = false
    private var floatingButtonMarginWhenCreated: Int = 0

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
        initViewsParams()
        initExtendedFAT()
    }

    private fun initViewsParams() {

//        binding.bottomAppBarMenuActivity.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
//
//
//            val bottomMarginParams = binding.linearlayoutMenuActivity.layoutParams as ViewGroup.MarginLayoutParams
//            bottomMarginParams.bottomMargin = v.height
//            val teste = v.height
//            binding.linearlayoutMenuActivity.layoutParams = bottomMarginParams
//        }
    }

    override fun hasBeenCheckboxChecked(v: FoodCardViewHolder, isCheckboxChecked: Boolean) {


    }

    private fun initTabLayoutSpecifications() {

        val tabsNameId = arrayOf(
            R.string.tab_item_entradas,
            R.string.tab_item_pratos_principais,
            R.string.tab_item_bebidas,
            R.string.tab_item_sobremesas,
        )
        tabFragmentsInstances = arrayOf(
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.ENTRADA),
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.PRINCIPAL),
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.BEBIDA),
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.SOBREMESA),
        )
        tabFragmentsInstances.forEach {
            it.setClickNotifyBridge(this)
            it.setScrollListenerForNotify(this)
        }
        val foods = ArrayList<Food>()
        val database = RestaurantDatabase.getInstance(this)
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
        val tabLayout = binding.tabFoodSectionsMenuActivity
        val viewPager2 = binding.pagerFoodSectionsMenuActivity
        val fragmentTabAdapter =
            FragmentTabAdapter(this, baseContext, tabsNameId, tabFragmentsInstances)

        viewPager2.adapter = fragmentTabAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = getString(fragmentTabAdapter.tabsNameId[position])
        }.attach()
    }

    private fun initSearchBarSpecifications() {
        this.searchBarFoods = binding.searchBarFoodsMenuActivity
        this.searchViewFoods = binding.searchViewFoodsMenuActivity

        searchViewFoods.editText.setOnEditorActionListener { v, actionId, event ->
            searchBarFoods.setText(searchViewFoods.text)
            searchViewFoods.hide()
            false
        }

        searchViewFoods.setupWithSearchBar(searchBarFoods)
    }

    private fun initImageFilterButton() {

        this.imageFilterButton = binding.imageFilterButtonMenuActivity
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

    private fun initExtendedFAT() {

        binding.floatingButtonCancelFoodOrder.setOnClickListener(this)
        binding.floatingButtonPayFoods.setOnClickListener(this)

    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.floatingButtonCancelFoodOrder -> {

                tabFragmentsInstances.forEach { it.cancelFoodSelected() }
            }
        }
    }

    override fun hasBeenScrolled(data: ScrollingView, dx: Int, dy: Int) {


        if (dy != 0) {

            hideFloatingButtonVertically(data, dy)
        }

    }

    private fun hideFloatingButtonVertically(recyclerViewOfFoodCards: ScrollingView, dy: Int) {

    }
}
