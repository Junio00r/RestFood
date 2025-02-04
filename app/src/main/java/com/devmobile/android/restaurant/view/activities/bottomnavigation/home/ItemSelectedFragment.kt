package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devmobile.android.restaurant.databinding.FragmentItemSelectedBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.ItemSelectedRemoteRepository
import com.devmobile.android.restaurant.usecase.ClickHandler
import com.devmobile.android.restaurant.view.adapters.ComplementaryItemsAdapter
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.ItemBetweenUiAndVM
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.ItemSelectedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemSelectedFragment : Fragment() {

    private val binding: FragmentItemSelectedBinding by lazy {
        FragmentItemSelectedBinding.inflate(layoutInflater)
    }
    private val viewModel: ItemSelectedViewModel by viewModels {
        ItemSelectedViewModel.provideFactory(safeArgs.itemId, safeArgs.restaurantId, repository)
    }
    private val repository: ItemSelectedRemoteRepository by lazy {
        ItemSelectedRemoteRepository(
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
        setUpObservables()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpObservables() {
        val touchListener = View.OnTouchListener { v, event ->

            onButtonClick(v, event.action)
            return@OnTouchListener true
        }
        binding.buttonDecrement.setOnTouchListener(touchListener)
        binding.buttonIncrement.setOnTouchListener(touchListener)

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.textOrderObservation.textInputEditText.doAfterTextChanged { text ->

            viewModel.updateItemObservation(text.toString())
        }

        binding.buttonAddItem.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }

        // ViewModel
        lifecycleScope.launch {
            viewModel.requiredSides.collect { lists ->
                setUpExpandableList(lists)
            }
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

    private fun setUpExpandableList(newRequiredSides: List<ItemBetweenUiAndVM>) {
        currentRequiredSides = newRequiredSides

        if (itemsAdapter == null) {

            itemsAdapter =
                ComplementaryItemsAdapter(requireContext(), newRequiredSides) { itemAdded ->
                    // q: anything
                }
            binding.recyclerComplementaryItems.adapter = itemsAdapter

        } else {
            itemsAdapter!!.updateRequiredSides(newRequiredSides)
        }
    }
}