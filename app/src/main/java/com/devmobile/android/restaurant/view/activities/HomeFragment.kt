package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.devmobile.android.restaurant.databinding.FragmentRestaurantHomeBinding
import com.devmobile.android.restaurant.viewmodel.BottomNavigationViewModel


class HomeFragment : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "HOME"
    }

    private var fragmentId: Int? = null

    private val _binding: FragmentRestaurantHomeBinding by lazy {
        FragmentRestaurantHomeBinding.inflate(this@HomeFragment.layoutInflater)
    }

    private val parentViewModel: BottomNavigationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            fragmentId = it.getInt("FRAGMENT_INDEX")
        }
        fragmentId?.let { parentViewModel.updateIndex(it) }

        return _binding.root
    }
}