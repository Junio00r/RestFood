package com.devmobile.android.restaurant

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.TranslateAnimation
import android.widget.PopupMenu
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.core.view.ScrollingView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.adapters.FoodCardAdapter
import com.devmobile.android.restaurant.adapters.FragmentTabAdapter
import com.devmobile.android.restaurant.databinding.ActivityMenuBinding
import com.devmobile.android.restaurant.enums.FoodSection
import com.devmobile.android.restaurant.enums.TempoPreparo
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import com.devmobile.android.restaurant.viewholders.FragmentTabFoodSection
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class MenuActivity : AppCompatActivity(), ClickNotification, View.OnClickListener, Scrolled {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var customAdapter: FoodCardAdapter
    private lateinit var recyclerViewFoods: RecyclerView
    private lateinit var searchBarFoods: SearchBar
    private lateinit var searchViewFoods: SearchView
    private lateinit var imageFilterButton: ImageFilterButton
    private lateinit var bottomSheet: RelativeLayout
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<RelativeLayout>
    private lateinit var tabFragmentsInstances: Array<FragmentTabFoodSection>
    private var floatingButtonStillCanScrolledToDown = true
    private var floatingButtonStillCanScrolledToUp = false
    private var floatingButtonMarginWhenCreated = 0

//    private lateinit var inputQuantity: TextInputEditText

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
        if (!binding.floatingButtonCancelFoodOrder.isShown) {
            binding.floatingButtonCancelFoodOrder.show()
            binding.floatingButtonPayFoods.show()
        } else {

            tabFragmentsInstances.all {

                if (it.getQuantityOfFoodsChecked() == 0) {

                    floatingButtonHide()
                    return@all true
                }

                return@all false
            }
        }
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
        val foodDao = RestaurantDatabase.getInstance(this).getFoodDao()
        foodDao.deleteAllTable()
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
                        "Hamburger",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        8,
                        "Hamburger",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        9,
                        "Hamburger",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        10,
                        "Hamburger",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        11,
                        "Hamburger",
                        FoodSection.ENTRADA,
                        R.drawable.hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        12,
                        "Hamburger",
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
        binding.floatingButtonCancelFoodOrder.hide()
        binding.floatingButtonPayFoods.hide()

        binding.floatingButtonCancelFoodOrder.setOnClickListener(this)
        binding.floatingButtonPayFoods.setOnClickListener(this)

        floatingButtonMarginWhenCreated = binding.floatingButtonCancelFoodOrder.marginBottom
    }

    private fun floatingButtonHide() {

        binding.floatingButtonCancelFoodOrder.hide()
        binding.floatingButtonPayFoods.hide()
    }

    override fun onClick(v: View) {

        if (v.id == R.id.floatingButtonCancelFoodOrder) {
            tabFragmentsInstances.forEach { it.cancelFoodSelected() }
            floatingButtonHide()
        }
    }

    override fun hasBeenScrolled(data: ScrollingView, dx: Int, dy: Int) {

        if (dy != 0) {

            if (binding.floatingButtonCancelFoodOrder.isShown)
                hideFloatingButtonVertically(data, dy)

        } else {

            // Inplemente para esconder algum botao horizontalmente caso deseje
        }
    }

    private fun hideFloatingButtonVertically(data: ScrollingView, dy: Int) {
        val floatingButCancelMarginLayout =
            binding.floatingButtonCancelFoodOrder.layoutParams as MarginLayoutParams
        val floatingButPayMarginLayout =
            binding.floatingButtonPayFoods.layoutParams as MarginLayoutParams

        if (dy > 0) {

            if (floatingButtonStillCanScrolledToDown) {

                if ((floatingButCancelMarginLayout.bottomMargin - dy) >= -binding.floatingButtonCancelFoodOrder.height) {

                    floatingButCancelMarginLayout.bottomMargin -= dy
                    floatingButPayMarginLayout.bottomMargin -= dy
                    floatingButtonStillCanScrolledToUp = true
                } else {

                    floatingButCancelMarginLayout.bottomMargin -= abs(binding.floatingButtonCancelFoodOrder.height - floatingButCancelMarginLayout.bottomMargin - dy)
                    floatingButPayMarginLayout.bottomMargin -= abs(binding.floatingButtonPayFoods.height - floatingButPayMarginLayout.bottomMargin - dy)
                    floatingButtonStillCanScrolledToDown = false
                }

                binding.floatingButtonCancelFoodOrder.layoutParams = floatingButCancelMarginLayout
                binding.floatingButtonPayFoods.layoutParams = floatingButPayMarginLayout
            }

        } else {

            if (floatingButtonStillCanScrolledToUp) {

                if (floatingButCancelMarginLayout.bottomMargin + abs(dy) <= floatingButtonMarginWhenCreated) {

                    floatingButCancelMarginLayout.bottomMargin += abs(dy)
                    floatingButPayMarginLayout.bottomMargin += abs(dy)
                    floatingButtonStillCanScrolledToDown = true
                } else {

                    binding.floatingButtonCancelFoodOrder.layoutParams = floatingButCancelMarginLayout
                    binding.floatingButtonPayFoods.layoutParams = floatingButPayMarginLayout
                    floatingButtonStillCanScrolledToUp = false
                }

                binding.floatingButtonCancelFoodOrder.layoutParams = floatingButCancelMarginLayout
                binding.floatingButtonPayFoods.layoutParams = floatingButPayMarginLayout
            }
        }
    }

    //        if (v.id == R.id.buttonDecrementQuantityBottomSheet) {
//
//            if (getEdittextFoodQuantity() - 1 >= 0) {
//
//                inputQuantity.setText((getEdittextFoodQuantity() - 1).toString())
//            }
//
//        } else if (v.id == R.id.buttonIncrementQuantityBottomSheet) {
//
//            if ((getEdittextFoodQuantity() + 1).toString()
//                    .count() <= inputQuantity.getMaxLength()
//            ) {
//
//                inputQuantity.setText((getEdittextFoodQuantity() + 1).toString())
//            }
//        }
//    }
//
//    private fun TextInputEditText.getMaxLength(): Int {
//        filters.forEach {
//            if (it is InputFilter.LengthFilter) {
//                return it.max
//            }
//        }
//
//        return -1
//    }
//
//    private fun getEdittextFoodQuantity(): Int {
//
//        return binding.edittextFoodQuantityPedidoBottomSheet.text.toString().toInt()
//    }
}
