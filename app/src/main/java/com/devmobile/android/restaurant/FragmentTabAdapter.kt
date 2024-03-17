package com.devmobile.android.restaurant

import android.content.Context
import androidx.compose.ui.util.fastFilter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.devmobile.android.restaurant.recyclerview.FoodCustomAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.LinkedList
import kotlin.math.absoluteValue

internal class FragmentTabAdapter(fragment: FragmentActivity, context: Context) :
    FragmentStateAdapter(fragment) {

    val tabs = arrayOf(
        R.string.tab_item_entradas,
        R.string.tab_item_pratos_principais,
        R.string.tab_item_bebidas,
        R.string.tab_item_sobremesas,
        R.string.tab_todos_itens
    )
    private val fragments = arrayOf(
        FragmentRestaurant(),
        FragmentRestaurant(),
        FragmentRestaurant(),
        FragmentRestaurant(),
        FragmentRestaurant()
    )
    private val foodsLocal = LinkedList<Food>()

    init {

        setFoods()
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {


        val teste = LinkedList<Food>()
        teste.addAll(
            foodsLocal.fastFilter { it.foodSection == FoodSection.ENTRADA }
        )
        fragments[position].foods = teste


        return fragments[position]
    }

    private fun setFoods() {

        foodsLocal.addAll(
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
                ),
                Food(
                    "Camarão",
                    FoodSection.TODAS,
                    R.drawable.camarao,
                    "Camarao do Mar"
                ),
                Food(
                    "Queijo",
                    FoodSection.TODAS,
                    R.drawable.queijo,
                    "Queijo Fresco"
                ),
                Food(
                    "Sopa",
                    FoodSection.TODAS,
                    R.drawable.sopa,
                    "Sopa de Carne"
                )
            )
        )
    }
}


