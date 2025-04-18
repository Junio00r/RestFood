package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devmobile.android.restaurant.R
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
    private val parentViewModel: BagSharedViewModel by activityViewModels()

    private val safeArgs: ItemSelectedFragmentArgs by navArgs()
    private val actionHandler = ClickHandler(lifecycleScope)
    private var itemsAdapter: ComplementaryItemsAdapter? = null
    private var currentRequiredSides: List<ItemBetweenUiAndVM> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.viewModel = parentViewModel
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner

        fetchItem()
        setUpObservables()

        return binding.root
    }

    private fun fetchItem() {

        parentViewModel.fetchItem(safeArgs.restaurantId, safeArgs.itemId)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUpObservables() {
        // Required items
        lifecycleScope.launch {
            parentViewModel.newRequiredSides
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { lists ->
                    lists?.let {
                        loadRequiredItems(lists)
                    }
                }
        }

        binding.textOrderObservation.textInputEditText.doAfterTextChanged {
            parentViewModel.updateItemObservation(it.toString())
        }

        val touchListener = View.OnTouchListener { v, event ->
            onButtonClick(v, event.action)
            return@OnTouchListener true
        }
        binding.buttonDecrement.setOnTouchListener(touchListener)
        binding.buttonIncrement.setOnTouchListener(touchListener)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onFinishScreen(false)
        }

        binding.buttonToolBar.setNavigationOnClickListener {
            onFinishScreen(false)
        }

        binding.buttonAddItem.setOnClickListener {

            parentViewModel.addItemOnBag(safeArgs.itemId)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                parentViewModel.wasItemAdded.collect { requestState ->

                    when (requestState) {

                        is RequestState.Loading -> {
                            // Add button animation
                        }

                        is RequestState.Success -> {
                            // Add success message?
                            // After add we're go back to the previous screen
                            Log.i("DEBUGGING", "Item selected and add at bag")
                            onFinishScreen(true)
                        }

                        is RequestState.Error -> {
                            // Add error message
                        }

                        null -> {
                            // Nothing
                        }
                    }
                }
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

                                parentViewModel.incrementTrigger()
                            } else {

                                parentViewModel.decrementTrigger()
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

    private fun onFinishScreen(wasItemAdd: Boolean) {

        setFragmentResult("requestAddToBagKey", bundleOf("bundleKey" to wasItemAdd))
        findNavController().popBackStack()
    }

    private fun loadRequiredItems(newRequiredSides: List<ItemBetweenUiAndVM>) {
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