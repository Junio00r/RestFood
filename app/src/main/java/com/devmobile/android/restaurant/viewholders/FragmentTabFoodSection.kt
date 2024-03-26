package com.devmobile.android.restaurant.viewholders

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.ClickNotification
import com.devmobile.android.restaurant.CustomChipFilter
import com.devmobile.android.restaurant.Food
import com.devmobile.android.restaurant.ModalBottomSheet
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.RestaurantDatabase
import com.devmobile.android.restaurant.Scrolled
import com.devmobile.android.restaurant.adapters.FoodCardAdapter
import com.devmobile.android.restaurant.databinding.TabFoodSectionLayoutBinding
import com.devmobile.android.restaurant.enums.FoodSection
import java.util.LinkedList

@SuppressLint("MissingInflatedId", "ResourceType")
class FragmentTabFoodSection(
    private val context: Context, private val fragmentLayoutId: Int, fragmentSection: FoodSection
) : Fragment(), ClickNotification {
    private var id: Int? = null

    private lateinit var binding: TabFoodSectionLayoutBinding
    lateinit var recyclerViewFoods: RecyclerView
    private var foodCardAdapter: FoodCardAdapter? = null
    private lateinit var foodDAO: RestaurantDatabase
    private lateinit var dataFoodsOfTabSections: ArrayList<Food>
    private var mFragmentSection = fragmentSection
    private val iconSize = 64f
    private val filtersChip = LinkedList<CustomChipFilter>()
    private val bottomSheet = ModalBottomSheet()
    private lateinit var testeando: View
    private var clickNotification: ClickNotification? = null
    private var isBottomSheetInflacting = false
    private lateinit var scrollListener: Scrolled

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Criando aqui ao inves de mandar pelo construtor
        return LayoutInflater.from(context).inflate(R.layout.tab_food_section_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = TabFoodSectionLayoutBinding.bind(view)
        foodDAO = RestaurantDatabase.getInstance(context)
        dataFoodsOfTabSections =
            foodDAO.getFoodDao().getFoodsBySection(mFragmentSection) as ArrayList<Food>

        recyclerViewFoods = binding.recyclerFood
        foodCardAdapter = FoodCardAdapter(dataFoodsOfTabSections, context)
        foodCardAdapter!!.setClickNotifyBridge(this)
        recyclerViewFoods.adapter = foodCardAdapter
        recyclerViewFoods.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        recyclerViewFoods.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                scrollListener.hasBeenScrolled(recyclerView, dx, dy)
            }
        })

//        loadChipsOnFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    fun setClickNotifyBridge(clickNotification: ClickNotification) {
        if (this.clickNotification == null) {

            this.clickNotification = clickNotification
        }
    }

    override fun hasBeenCheckboxChecked(v: FoodCardViewHolder, isCheckboxChecked: Boolean) {

        synchronized(Any()) {

            if (isCheckboxChecked) {

                bottomSheet.show(this.childFragmentManager, ModalBottomSheet.TAG)
                bottomSheet.setBottomSheetAttributes(v)
                clickNotification!!.hasBeenCheckboxChecked(v, true)


            } else {
                clickNotification!!.hasBeenCheckboxChecked(v, false)
                bottomSheet.dismiss()
            }
        }
    }

    fun cancelFoodSelected() {

        foodCardAdapter?.getFoodCardViewHoldersSelected().let { viewHolderLinkedList ->

            viewHolderLinkedList?.forEach { foodCardViewHolder ->
                foodCardAdapter!!.hasBeenCheckboxChecked(
                    foodCardViewHolder, true
                )
            }

            // Remove all CardViewHolders Save
            viewHolderLinkedList?.removeAll { !it.isCheckboxChecked }
        }
    }

    fun getQuantityOfFoodsChecked(): Int {

        return foodCardAdapter?.getFoodCardViewHoldersSelected()?.size ?: 0
    }

    fun setScrollListenerForNotify(scrollListener: Scrolled) {
        this.scrollListener = scrollListener
    }

//    private fun loadChipsOnFragment() {
//        if (filtersChip.size == 0) {
//
//            createChips()
//            binding.let {
//                filtersChip.forEach { filter ->
//
//                    if (filter.parent != null) {
//                        (filter.parent as ViewGroup).removeView(filter)
//                    }
//                    it.chipGroupFilter.addView(filter)
//                }
//            }
//
//        } else {
//
//            binding.let {
//                filtersChip.forEach { filter ->
//
//                    if (filter.parent != null) {
//                        (filter.parent as ViewGroup).removeView(filter)
//                    }
//                    it.chipGroupFilter.addView(filter)
//                }
//            }
//        }
//    }
//
//    private fun createChips() {
//
//        filtersChip.addAll(
//            arrayOf(
//                CustomChipFilter(
//                    requireContext(), "Ordenar", iconSize, null, R.drawable.ic_chip_filter
//                ),
//                CustomChipFilter(
//                    requireContext(),
//                    "Mais Recente",
//                    iconSize,
//                    R.drawable.ic_check_chip_filter,
//                    null
//                ),
//                CustomChipFilter(
//                    requireContext(),
//                    "Preparo Rápido",
//                    iconSize,
//                    R.drawable.ic_check_chip_filter,
//                    null
//                ),
//                CustomChipFilter(
//                    requireContext(),
//                    "Preparo Lento",
//                    iconSize,
//                    R.drawable.ic_check_chip_filter,
//                    null
//                ),
//            )
//        )
//    }
}