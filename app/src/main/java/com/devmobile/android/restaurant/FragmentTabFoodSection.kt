package com.devmobile.android.restaurant

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devmobile.android.restaurant.adapters.FoodCardAdapter
import com.devmobile.android.restaurant.databinding.TabFoodSectionLayoutBinding
import com.devmobile.android.restaurant.enums.FoodSection
import com.devmobile.android.restaurant.viewholders.FoodCardViewHolder
import java.util.LinkedList

class FragmentTabFoodSection(fragmentLayoutId: Int, fragmentSection: FoodSection) :
    Fragment(fragmentLayoutId), ClickNotification {
    private var id: Int? = null

    private lateinit var binding: TabFoodSectionLayoutBinding
    private lateinit var recyclerViewFoods: RecyclerView
    private lateinit var foodCardAdapter: FoodCardAdapter
    private lateinit var foodDAO: RestaurantDatabase
    private var dataFoodsOfTabSections = ArrayList<Food>()
    private var mFragmentSection = fragmentSection
    private val iconSize = 64f
    private val filtersChip = LinkedList<CustomChipFilter>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(
            requireContext(),
            "Chegou onCreateView $mFragmentSection",
            Toast.LENGTH_SHORT
        ).show()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding = TabFoodSectionLayoutBinding.bind(view)
        foodDAO = RestaurantDatabase.getInstance(requireContext())
        dataFoodsOfTabSections =
            foodDAO.getFoodDao().getFoodsBySection(mFragmentSection) as ArrayList<Food>
        dataFoodsOfTabSections.size
        recyclerViewFoods = binding.recyclerFood
        foodCardAdapter = FoodCardAdapter(dataFoodsOfTabSections, requireContext())
        foodCardAdapter.setClickNotifyBridge(this)
        recyclerViewFoods.adapter = foodCardAdapter
        recyclerViewFoods.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//        loadChipsOnFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Toast.makeText(
            requireContext(),
            "Chegou onViewStateRestored $mFragmentSection",
            Toast.LENGTH_SHORT
        ).show()
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Toast.makeText(requireContext(), "Chegou onStart $mFragmentSection", Toast.LENGTH_SHORT)
            .show()
        super.onStart()
    }

    override fun onResume() {
        Toast.makeText(requireContext(), "Chegou onResume $mFragmentSection", Toast.LENGTH_SHORT)
            .show()
        // Solicito para redesenhar as views
        recyclerViewFoods.invalidate()

        super.onResume()
    }

    override fun onPause() {
        Toast.makeText(requireContext(), "Chegou onPause $mFragmentSection", Toast.LENGTH_SHORT)
            .show()
        super.onPause()
    }

    override fun onStop() {
        Toast.makeText(requireContext(), "Chegou onStop $mFragmentSection", Toast.LENGTH_SHORT)
            .show()
        super.onStop()
    }

    override fun onDestroy() {
        Toast.makeText(requireContext(), "Chegou onDestroy $mFragmentSection", Toast.LENGTH_SHORT)
            .show()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {

        Toast.makeText(
            requireContext(),
            "Chegou onSaveInstanceState $mFragmentSection",
            Toast.LENGTH_SHORT
        ).show()
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Toast.makeText(
            requireContext(),
            "Chegou onDestroyView $mFragmentSection",
            Toast.LENGTH_SHORT
        ).show()
        super.onDestroyView()
    }

    @SuppressLint("ResourceAsColor")
    override fun checkboxClicked(v: FoodCardViewHolder) {

        val bottomSheet = ModalBottomSheet()
        bottomSheet.show(this.parentFragmentManager, ModalBottomSheet.TAG)
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