package com.devmobile.android.restaurant.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.FoodSelectedCallback
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.RestaurantDatabase
import com.devmobile.android.restaurant.adapters.FragmentTabAdapter
import com.devmobile.android.restaurant.databinding.ActivityMenuBinding
import com.devmobile.android.restaurant.enums.FoodSection
import com.devmobile.android.restaurant.enums.TempoPreparo
import com.devmobile.android.restaurant.viewholders.FragmentTabFoodSection
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Locale


class MenuActivity : AppCompatActivity(), View.OnClickListener, FoodSelectedCallback {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var tabFragmentsInstances: Array<FragmentTabFoodSection>
    private lateinit var floatingButtonCancelFoodOrder: MaterialButton
    private lateinit var floatingButtonPayFoods: MaterialButton
    private lateinit var fragmentTabAdapter: FragmentTabAdapter
    private var dataToRealizarPagamento = ArrayList<Array<*>>()

    init {

        Locale.setDefault(Locale("pt", "BR"))
    }

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

        val teste = RestaurantDatabase.getInstance(this)
        teste.clearAllTables()

        addFoodsInDatabase()
        setTabLayouts()
        setExtendedFAT()
    }

    // Métodos de inicialização...
    private fun setTabLayouts() {

        val tabsNameId = addTabsName()

        tabFragmentsInstances = addFragmentsTabSection()
        tabFragmentsInstances.forEach {

//                        it.addCheckboxClickListener(this)
            it.addFoodAddedCallback(this)
        }

        val tabLayout = binding.tabFoodSectionsMenuActivity
        val viewPager2 = binding.pagerFoodSectionsMenuActivity
        fragmentTabAdapter = FragmentTabAdapter(this, tabFragmentsInstances)

        viewPager2.adapter = fragmentTabAdapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = getString(tabsNameId[position])
        }.attach()
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

    private fun addFoodsInDatabase() {

        val foods = ArrayList<Food>()
        val foodDao = RestaurantDatabase.getInstance(this).getFoodDao()

        if (foodDao.getFoodsSize() == 0) {
            foods.addAll(
                listOf(
                    Food(
                        0,
                        "Macarronada",
                        87F,
                        FoodSection.ENTRADA,
                        R.drawable.image_macarronada,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.LENTO,
                        "Macarronado com Salsicha"
                    ), Food(
                        1,
                        "Hamburger",
                        35F,
                        FoodSection.ENTRADA,
                        R.drawable.image_hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        2,
                        "Lasanha",
                        55F,
                        FoodSection.ENTRADA,
                        R.drawable.image_lasanha,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.LENTO,
                        "Lasanha Irlandesa"
                    ), Food(
                        3,
                        "Feijoada",
                        133F,
                        FoodSection.ENTRADA,
                        R.drawable.image_feijoada,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.LENTO,
                        "Feijoada Brasileira"
                    ), Food(
                        4,
                        "Camarão",
                        60F,
                        FoodSection.ENTRADA,
                        R.drawable.image_camarao,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.NORMAL,
                        "Camarao do Mar"
                    ), Food(
                        5,
                        "Queijo",
                        30F,
                        FoodSection.ENTRADA,
                        R.drawable.image_queijo,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Queijo Fresco"
                    ), Food(
                        6,
                        "Sopa",
                        40F,
                        FoodSection.ENTRADA,
                        R.drawable.image_sopa,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.NORMAL,
                        "Sopa de Carne"
                    ), Food(
                        7,
                        "Hamburg32er",
                        30F,
                        FoodSection.ENTRADA,
                        R.drawable.image_hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        133,
                        "Casca de Ovo",
                        30F,
                        FoodSection.ENTRADA,
                        R.drawable.image_hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        7878,
                        "Pudin",
                        30F,
                        FoodSection.ENTRADA,
                        R.drawable.image_hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        144,
                        "Peixada",
                        30F,
                        FoodSection.ENTRADA,
                        R.drawable.image_hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    ), Food(
                        98,
                        "Bolo",
                        30F,
                        FoodSection.ENTRADA,
                        R.drawable.image_hamburguer,
                        R.drawable.ic_time_prepare_lento,
                        TempoPreparo.RAPIDO,
                        "Big Hamburger"
                    )
                )
            )

            foodDao.insertAll(foods as List<Food>)
        }
    }

    private fun setExtendedFAT() {

        floatingButtonCancelFoodOrder = binding.floatingButtonCancelFoodOrder
        floatingButtonPayFoods = binding.floatingButtonPayFoods

        floatingButtonCancelFoodOrder.setOnClickListener(this)
        floatingButtonPayFoods.setOnClickListener(this)
    }

    private fun showWarning() {

        val message = "Selecione uma Comida para Realizar o Pedido!"
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)

        toast.setGravity(Gravity.BOTTOM, 0, 440)
        toast.show()
    }

    override fun onClick(v: View) {

        when (v) {

            floatingButtonCancelFoodOrder -> {

                if (canPlaceOrder()) tabFragmentsInstances.forEach { it.cancelFoodSelected() }
            }

            floatingButtonPayFoods -> {

                if (canPlaceOrder()) startFinalizeOrderFragmentActivity()
                else showWarning()
            }
        }
    }

    private fun canPlaceOrder(): Boolean {

        return fragmentTabAdapter.getQuantityOfFoodsSelected() > 0
    }

    private fun startFinalizeOrderFragmentActivity() {

        val intent = Intent(this, FinalizeOrderFragment::class.java)
        insertDataInIntent(intent)

        startActivity(intent)
    }

    private fun insertDataInIntent(intent: Intent) {
        var foodId: Long
        var foodName: String
        var foodPrice: Float
        var foodSectionOrdinal: Int
        var quantityAdded: Int

        dataToRealizarPagamento.forEach {
            foodId = it[0].toString().toLong()
            foodName = it[1].toString()
            foodPrice = it[2].toString().toFloat()
            foodSectionOrdinal = it[3].toString().toInt()
            quantityAdded = it[4].toString().toInt()

            intent.putExtra("foodId", foodId)
            intent.putExtra("foodName", foodName)
            intent.putExtra("foodPrice", foodPrice)
            intent.putExtra("foodSectionOrdinal", foodSectionOrdinal)
            intent.putExtra("quantityAdded", quantityAdded)
        }
    }

    // Métodos de implementacoes de interfaces criadas
    override fun onAddedFood(
        foodId: Long,
        foodName: String?,
        foodPrice: Float?,
        sectionOnSelectedFoodOrdinal: FoodSection?,
        quantityAdded: Int?
    ) {

        dataToRealizarPagamento.add(
            arrayOf(foodId, foodName, foodPrice, sectionOnSelectedFoodOrdinal!!.ordinal, quantityAdded)
        )
    }

    override fun onRemoveFood(foodId: Long, sectionOnSelectedFood: FoodSection?) {

        dataToRealizarPagamento.removeIf {
            it[0] == foodId
        }
    }
}
