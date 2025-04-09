package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devmobile.android.restaurant.databinding.FragmentItemSelectedBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.datasource.local.entities.ItemBetweenUiAndVM
import com.devmobile.android.restaurant.model.repository.BagRemoteRepository
import com.devmobile.android.restaurant.usecase.ClickHandler
import com.devmobile.android.restaurant.usecase.RequestState
import com.devmobile.android.restaurant.view.adapters.ComplementaryItemsAdapter
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.BagSharedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemSelectedFragment : Fragment() {

    private val binding: FragmentItemSelectedBinding by lazy {
        FragmentItemSelectedBinding.inflate(layoutInflater)
    }
    private val viewModel: BagSharedViewModel by activityViewModels {
        BagSharedViewModel.provideFactory(null, null, repository)
    }
    private val repository: BagRemoteRepository by lazy {
        BagRemoteRepository(
            RestaurantLocalDatabase.getInstance(requireContext()).getItemDao()
        )
    }
    private val safeArgs: ItemSelectedFragmentArgs by navArgs()

    private val actionHandler = ClickHandler(lifecycleScope)
    private var itemsAdapter: ComplementaryItemsAdapter? = null
    private var currentRequiredSides: List<ItemBetweenUiAndVM> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        loadItem()
        setUpObservables()

        return binding.root
    }

    private fun loadItem() {

        viewModel.fetchItem(safeArgs.restaurantId, safeArgs.itemId)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpObservables() {
        val touchListener = View.OnTouchListener { v, event ->

            onButtonClick(v, event.action)
            return@OnTouchListener true
        }
        binding.buttonDecrement.setOnTouchListener(touchListener)
        binding.buttonIncrement.setOnTouchListener(touchListener)

        // All items
        lifecycleScope.launch {
            viewModel.newRequiredSides.collect { lists ->
                lists?.let {
                    addRequiredItems(lists)
                }
            }
        }

        // After add go back to previous screen
        binding.buttonAddItem.setOnClickListener {

            viewModel.addItemOnBag(safeArgs.itemId)

            setFragmentResult("requestAddToBagKey", bundleOf("bundleKey" to true))

            viewModel.cancelItemSelection()
            findNavController().popBackStack()
        }

        binding.toolBar.setNavigationOnClickListener {
            viewModel.cancelItemSelection()
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.cancelItemSelection()
            findNavController().popBackStack()
        }
    }

    private fun onButtonClick(view: View, action: Int) {

        when (action) {

            MotionEvent.ACTION_DOWN -> {

                if (!actionHandler.hasJobs()) {
                    actionHandler.run {
                        while (true) {

                            if (view.id == binding.buttonIncrement.id) {

                                viewModel.incrementTrigger()
                            } else {

                                viewModel.decrementTrigger()
                            }
                            delay(200)
                        }
                    }
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

                actionHandler.cancelAll()
            }
        }
    }

    private fun addRequiredItems(newRequiredSides: List<ItemBetweenUiAndVM>) {
        currentRequiredSides = newRequiredSides

        if (itemsAdapter == null) {

            itemsAdapter = ComplementaryItemsAdapter(requireContext(), currentRequiredSides) { itemAdded ->
                    // q: anything
                }
            binding.recyclerComplementaryItems.adapter = itemsAdapter

        } else {
            itemsAdapter!!.updateRequiredSides(newRequiredSides)
        }
    }
}