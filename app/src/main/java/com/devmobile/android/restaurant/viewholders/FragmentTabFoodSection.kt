package com.devmobile.android.restaurant.viewholders

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.CheckboxClickListener
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.FoodSelectedCallback
import com.devmobile.android.restaurant.ModalBottomSheet
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.RestaurantDatabase
import com.devmobile.android.restaurant.adapters.FoodCardAdapter
import com.devmobile.android.restaurant.databinding.TabFoodSectionLayoutBinding
import com.devmobile.android.restaurant.enums.FoodSection

class FragmentTabFoodSection(

    private val context: Context, private val fragmentLayoutId: Int, fragmentSection: FoodSection

) : Fragment(), CheckboxClickListener, FoodSelectedCallback {

    private lateinit var binding: TabFoodSectionLayoutBinding
    private lateinit var recyclerViewFoods: RecyclerView
    private var foodCardAdapter: FoodCardAdapter? = null
    private lateinit var foodDAO: RestaurantDatabase
    private lateinit var dataFoodsOfTabSections: ArrayList<Food>
    private var mFragmentSection = fragmentSection
    private var onFoodAddedCallback: FoodSelectedCallback? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Criando aqui ao inves de colocar direto pelo contrutor
        return inflater.inflate(R.layout.tab_food_section_layout, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = TabFoodSectionLayoutBinding.bind(view)
        foodDAO = RestaurantDatabase.getInstance(requireContext())
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

    fun addFoodAddedCallback(onAddedCallbackOfMenuActivity: FoodSelectedCallback) {

        if (onFoodAddedCallback == null) onFoodAddedCallback = onAddedCallbackOfMenuActivity
    }

    override fun isCheckboxChecked(
        v: FoodCardViewHolder, isCheckboxChecked: Boolean
    ) {

        if (isCheckboxChecked) {

            val bottomSheet = ModalBottomSheet()
            bottomSheet.show(this.childFragmentManager, ModalBottomSheet.TAG)
            bottomSheet.setBottomSheetAttributes(v)
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
}