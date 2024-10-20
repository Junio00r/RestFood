package com.devmobile.android.restaurant.view.activities.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.devmobile.android.restaurant.databinding.FragmentAccountsBinding
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.BottomNavigationViewModel

class AccountFragment : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "ACCOUNT"
    }

    private var fragmentId: Int? = null
    private val _binding: FragmentAccountsBinding by lazy {
        FragmentAccountsBinding.inflate(this@AccountFragment.layoutInflater)
    }
    private val _parentViewModel: BottomNavigationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.let {
            fragmentId = it.getInt("FRAGMENT_INDEX")
        }
        fragmentId?.let { _parentViewModel.updateIndex(it) }

        return _binding.root
    }
}