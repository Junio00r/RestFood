package com.devmobile.android.restaurant

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.text.InputFilter
import android.text.PrecomputedText.Params
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.text.KeyboardActions
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.core.view.WindowCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.adapters.FoodCardAdapter
import com.devmobile.android.restaurant.adapters.FragmentTabAdapter
import com.devmobile.android.restaurant.databinding.ActivityMenuBinding
import com.devmobile.android.restaurant.enums.FoodSection
import com.devmobile.android.restaurant.enums.TempoPreparo
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var customAdapter: FoodCardAdapter
    private lateinit var recyclerViewFoods: RecyclerView
    private lateinit var searchBarFoods: SearchBar
    private lateinit var searchViewFoods: SearchView
    private lateinit var imageFilterButton: ImageFilterButton
    private lateinit var bottomSheet: RelativeLayout
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<RelativeLayout>
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

    companion object {
        private const val INITIAL_COUNT = 1
    }

    private fun init() {

        initSearchBarSpecifications()
        initTabLayoutSpecifications()
        initImageFilterButton()
    }

    @SuppressLint("ResourceAsColor")
    private fun initTabLayoutSpecifications() {

        val tabsNameId = arrayOf(
            R.string.tab_item_entradas,
            R.string.tab_item_pratos_principais,
            R.string.tab_item_bebidas,
            R.string.tab_item_sobremesas,
        )
        val tabFragmentsInstances = arrayOf(
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.ENTRADA),
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.PRINCIPAL),
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.BEBIDA),
            FragmentTabFoodSection(this, R.layout.tab_food_section_layout, FoodSection.SOBREMESA),
        )
//        tabFragmentsInstances.forEach { it.setClickNotifyBridge(this) }
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
                        FoodSection.PRINCIPAL,
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
                        FoodSection.PRINCIPAL,
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
                        FoodSection.PRINCIPAL,
                        R.drawable.sopa,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.NORMAL,
                        "Sopa de Carne"
                    )
                )
            )

            foodDao.insertAll(foods as List<Food>)
        }
        val tabLayout = binding.tabFoodSections
        val viewPager2 = binding.pagerFoodSections
        val fragmentTabAdapter =
            FragmentTabAdapter(this, baseContext, tabsNameId, tabFragmentsInstances)

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

//    override fun checkboxClicked(v: FoodCardViewHolder) {
//        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//    }

//    override fun onClick(v: View) {
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
