package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmobile.android.restaurant.databinding.FragmentFindItemBinding
import com.devmobile.android.restaurant.databinding.LayoutRecyclerviewItemsBinding
import com.devmobile.android.restaurant.model.datasource.local.entities.Item
import com.devmobile.android.restaurant.view.adapters.ItemAdapter
import com.devmobile.android.restaurant.view.adapters.TabAdapter
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.MenuManagerViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class MenuManagerFragment : Fragment() {

    private val _binding: FragmentFindItemBinding by lazy {
        FragmentFindItemBinding.inflate(layoutInflater)
    }
    private val parentViewModel: MenuManagerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setUpSearch()
        setUpTabs()
        setObservables()

        return _binding.root
    }

    private fun setObservables() {

        _binding.searchViewItems.editText.doOnTextChanged { text, _, _, _ ->

            lifecycleScope.launch {

                val newItems = parentViewModel.fetchItemsByPattern(text.toString())
                populateSearch(newItems)
            }
        }

        _binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

                tab?.let { parentViewModel.tabPosition = it.position }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        })
    }

    private fun setUpTabs() {

        lifecycleScope.launch {

            val tabsSections = parentViewModel.fetchSections()
            val tabs = createSections(tabsSections)

            _binding.viewPager2.offscreenPageLimit = 3
            _binding.viewPager2.adapter = TabAdapter(requireActivity(), tabs)
            _binding.viewPager2.setCurrentItem(parentViewModel.tabPosition, false)

            TabLayoutMediator(_binding.tabLayout, _binding.viewPager2) { tab, position ->

                tab.text = tabsSections[position]
            }.attach()
        }
    }

    private fun createSections(menuSections: List<String>): List<Fragment> {

        return menuSections.mapIndexed { _, sectionName ->

            MenuSection().apply {
                arguments = Bundle().apply {
                    putStringArray("ARGUMENTS", arrayOf("0", sectionName))
                }
            }
        }
    }

    private fun setUpSearch() {

        _binding.searchViewItems.setupWithSearchBar(_binding.searchBarItems)
    }

    private fun populateSearch(items: List<Item>) {

        _binding.recyclerSearchItem.adapter = ItemAdapter(items) { mustAdd, itemId ->

            if (mustAdd) {

                val action = MenuManagerFragmentDirections.actionFromMenuFragmentToItemSelected(
                    itemId,
                    parentViewModel.restaurantId
                )
                findNavController().navigate(action)
                _binding.searchViewItems.hide()
            }
        }
    }


    class MenuSection : Fragment() {

        private val _binding: LayoutRecyclerviewItemsBinding by lazy {
            LayoutRecyclerviewItemsBinding.inflate(layoutInflater)
        }
        private val parentViewModel: MenuManagerViewModel by activityViewModels()
        private var _items: ArrayList<Item> = arrayListOf()

        private val sectionName: String by lazy {
            requireArguments().getStringArray("ARGUMENTS")!![1]
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            lifecycleScope.launch {

                fetchItems()
                setUpSectionItems()
            }

            return _binding.root
        }

        private suspend fun fetchItems() {

            _items = parentViewModel.fetchItems(sectionName) as ArrayList
        }

        private fun setUpSectionItems() {

            _binding.recyclerItems.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )

            if (_items.isNotEmpty()) {

                _binding.recyclerItems.adapter = ItemAdapter(_items) { mustAdd, itemId ->

                    if (mustAdd) {

                        val action =
                            MenuManagerFragmentDirections.actionFromMenuFragmentToItemSelected(
                                itemId,
                                parentViewModel.restaurantId
                            )
                        findNavController().navigate(action)

                    } else {
                        parentViewModel.onUnselectedItem(itemId)
                    }
                }
            }
        }
    }
}