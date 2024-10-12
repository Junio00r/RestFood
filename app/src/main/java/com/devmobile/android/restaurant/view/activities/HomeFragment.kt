package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.devmobile.android.restaurant.databinding.FragmentRestaurantHomeBinding
import com.devmobile.android.restaurant.model.repository.HomeRepository
import com.devmobile.android.restaurant.viewmodel.BottomNavigationViewModel
import com.devmobile.android.restaurant.viewmodel.HomeViewModel

class HomeFragment(private val fragmentIndex: Int) : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "Home"
    }

    private val _binding: FragmentRestaurantHomeBinding by lazy {
        FragmentRestaurantHomeBinding.inflate(this@HomeFragment.layoutInflater)
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