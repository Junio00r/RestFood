package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.OptIn
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmobile.android.restaurant.databinding.FragmentMenuManagerItemsBinding
import com.devmobile.android.restaurant.databinding.LayoutRecyclerviewItemsBinding
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.view.adapters.ItemAdapter
import com.devmobile.android.restaurant.view.adapters.TabAdapter
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.MenuManagerViewModel
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.search.SearchView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MenuManagerFragment : Fragment() {

    private val _binding: FragmentMenuManagerItemsBinding by lazy {
        FragmentMenuManagerItemsBinding.inflate(layoutInflater)
    }
    private val parentViewModel: MenuManagerViewModel by activityViewModels()
    private var isSearchEnabled = false
    private var hasItemOnBag = false

    @OptIn(ExperimentalBadgeUtils::class)
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        savedInstanceState?.let {
            isSearchEnabled = it.getBoolean("PREVIOUS_SEARCH_VIEW_STATE")
        }

        if (!_binding.searchViewItems.isSetupWithSearchBar) {

            setUpSearch()
            setUpTabs()
            setUpObservables()
        }

        if (isSearchEnabled) {
            _binding.searchViewItems.show()
        }
        return _binding.root
    }

    override fun onResume() {

        _binding.buttonBagItems.foreground.alpha = if (hasItemOnBag) 255 else 0
        super.onResume()
    }

    private fun setUpSearch() {

        _binding.searchViewItems.setupWithSearchBar(_binding.searchBarItems)
    }

    private fun setUpObservables() {

        _binding.searchViewItems.editText.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launch {

                val newItems = parentViewModel.fetchItemsByPattern(text.toString())
                populateSearch(newItems)
            }
        }

        _binding.searchViewItems.addTransitionListener { searchView, previousState, newState ->

            isSearchEnabled = newState == SearchView.TransitionState.SHOWN
        }

        _binding.buttonBagItems.setOnClickListener {

            findNavController().navigate(MenuManagerFragmentDirections.actionFromMenuManagerFragmentToBag())
        }

        setFragmentResultListener("requestAddToBagKey") { requestKey, bundle ->

            hasItemOnBag = bundle.getBoolean("bundleKey", false)
        }

        _binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

                tab?.let { parentViewModel.tabPosition = it.position }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            _binding.searchViewItems.hide()
        }
    }

    private fun setUpTabs() {

        lifecycleScope.launch {

            val restaurantSections = parentViewModel.fetchSections()
            val tabs = createSections(restaurantSections)

            _binding.viewPager2.offscreenPageLimit = 3
            _binding.viewPager2.adapter = TabAdapter(requireActivity(), tabs)
            _binding.viewPager2.setCurrentItem(parentViewModel.tabPosition, false)

            TabLayoutMediator(_binding.tabLayout, _binding.viewPager2) { tab, position ->

                tab.text = restaurantSections[position]
            }.attach()
        }
    }

    private fun createSections(menuSections: List<String>): List<Fragment> {

        return menuSections.mapIndexed { _, sectionName ->
            MenuSection().apply {
                CoroutineScope(Job() + Dispatchers.Default).launch {
                    arguments = Bundle().apply {
                        putStringArray("ARGUMENTS", arrayOf("0", sectionName))
                    }
                }
            }
        }
    }

    private fun populateSearch(newItems: List<Item>) {

        if (_binding.recyclerSearchItem.adapter == null) {

            _binding.recyclerSearchItem.adapter = ItemAdapter(newItems) { mustAdd, itemId ->

                if (mustAdd) {

                    val action =
                        MenuManagerFragmentDirections.actionFromMenuManagerFragmentToItemSelected(
                            itemId,
                            parentViewModel.restaurantId
                        )
                    _binding.searchViewItems.clearFocus()
                    findNavController().navigate(action)
                }
            }
        } else {
            (_binding.recyclerSearchItem.adapter as ItemAdapter).updateItems(newItems)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("PREVIOUS_SEARCH_VIEW_STATE", isSearchEnabled)
    }

    class MenuSection : Fragment() {

        private val _binding: LayoutRecyclerviewItemsBinding by lazy {
            LayoutRecyclerviewItemsBinding.inflate(layoutInflater)
        }
        private val parentViewModel: MenuManagerViewModel by activityViewModels()
        private var _items: ArrayList<Item> = arrayListOf()

        // Isn't still passed this for navigation component, after I do
        private val sectionName: String by lazy {
            requireArguments().getStringArray("ARGUMENTS")!![1]
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View {

            lifecycleScope.launch(Dispatchers.Default) {

                fetchItems()
                setUpSectionItems()
            }

            return _binding.root
        }

        private suspend fun fetchItems() {

            _items = parentViewModel.fetchItems(sectionName) as ArrayList
        }

        private fun setUpSectionItems() {

            requireActivity().runOnUiThread {
                _binding.recyclerItems.addItemDecoration(
                    DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
                )

                if (_items.isNotEmpty()) {

                    _binding.recyclerItems.adapter = ItemAdapter(_items) { mustAdd, itemId ->

                        if (mustAdd) {

                            val action =
                                MenuManagerFragmentDirections.actionFromMenuManagerFragmentToItemSelected(
                                    itemId,
                                    parentViewModel.restaurantId
                                )

                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }
}