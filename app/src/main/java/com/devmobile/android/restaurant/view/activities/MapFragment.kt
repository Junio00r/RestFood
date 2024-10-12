package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.devmobile.android.restaurant.databinding.FragmentMapRestaurantsBinding
import com.devmobile.android.restaurant.viewmodel.BottomNavigationViewModel

class MapFragment(private val fragmentIndex: Int) : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "Map"
    }

    private val _binding: FragmentMapRestaurantsBinding by lazy {
        FragmentMapRestaurantsBinding.inflate(this@MapFragment.layoutInflater)
    }
    private val parentViewModel: BottomNavigationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        parentViewModel.updateIndex(fragmentIndex)

        return _binding.root
    }
}