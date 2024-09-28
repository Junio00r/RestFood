package com.devmobile.android.restaurant.view.viewholders

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.IOnCheckCheckbox
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.IOnSelectFood
import com.devmobile.android.restaurant.view.customelements.ModalBottomSheet
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.viewmodel.adapters.FoodCardAdapter
import com.devmobile.android.restaurant.databinding.LayoutRecyclerviewFoodsBinding
import com.devmobile.android.restaurant.usecase.enums.FoodSection

class FragmentTabFoodSection(

    private val context: Context, private val fragmentLayoutId: Int, fragmentSection: FoodSection

) : Fragment(), IOnCheckCheckbox, IOnSelectFood {

    private lateinit var binding: LayoutRecyclerviewFoodsBinding
    private lateinit var recyclerViewFoods: RecyclerView
    private var foodCardAdapter: FoodCardAdapter? = null
    private lateinit var foodDAO: RestaurantLocalDatabase
    private lateinit var dataFoodsOfTabSections: ArrayList<Food>
    private var mFragmentSection = fragmentSection
    private var onFoodAddedCallback: IOnSelectFood? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Criando aqui ao inves de colocar direto pelo contrutor
        return inflater.inflate(R.layout.layout_recyclerview_foods, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = LayoutRecyclerviewFoodsBinding.bind(view)
        foodDAO = RestaurantLocalDatabase.getInstance(requireContext())
        dataFoodsOfTabSections =
            mFragmentSection.let { foodDAO.getFoodDao().getFoodsBySection(it) } as ArrayList<Food>

        recyclerViewFoods = binding.recyclerFood
        foodCardAdapter = FoodCardAdapter(dataFoodsOfTabSections, requireContext())
        foodCardAdapter?.addCheckboxClickListener(this)
        recyclerViewFoods.adapter = foodCardAdapter
        recyclerViewFoods.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        super.onViewCreated(view, savedInstanceState)
    }

    fun cancelFoodSelected() {

        foodCardAdapter?.cancelOrder()
    }

    fun getQuantityOfFoodsSelected(): Int {

        return foodCardAdapter?.getFoodCardViewHoldersSelected()?.size ?: 0
    }

    fun addFoodAddedCallback(onAddedCallbackOfMenuActivity: IOnSelectFood) {

        if (onFoodAddedCallback == null) onFoodAddedCallback = onAddedCallbackOfMenuActivity
    }

    override fun isCheckboxChecked(
        v: FoodCardViewHolder, isCheckboxChecked: Boolean
    ) {

        if (isCheckboxChecked) {

            val bottomSheet = ModalBottomSheet()
            bottomSheet.show(this.childFragmentManager, ModalBottomSheet.TAG)
            bottomSheet.addFoodCardViewHolder(v)
            bottomSheet.addOnFoodAddedCallback(this)

        } else {

            onFoodAddedCallback?.onRemoveFood(v.foodId!!)
        }
    }

    override fun onAddedFood(
        foodId: Long,
        foodName: String?,
        foodPrice: Float?,
        sectionOnSelectedFoodOrdinal: FoodSection?,
        quantityAdded: Int?
    ) {

        onFoodAddedCallback?.onAddedFood(
            foodId, foodName, foodPrice, this.mFragmentSection, quantityAdded
        )
    }

    override fun onRemoveFood(foodId: Long, sectionOnSelectedFood: FoodSection?) {
        onFoodAddedCallback?.onRemoveFood(foodId)
    }

    // Para estudos
    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}