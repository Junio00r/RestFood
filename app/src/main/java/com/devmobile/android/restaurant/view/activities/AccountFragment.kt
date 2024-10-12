package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.devmobile.android.restaurant.databinding.FragmentAccountsBinding
import com.devmobile.android.restaurant.viewmodel.BottomNavigationViewModel

class AccountFragment(private val fragmentIndex: Int) : Fragment() {

    companion object {
        const val FRAGMENT_TAG = "Account"
    }

    private val _binding: FragmentAccountsBinding by lazy {
        FragmentAccountsBinding.inflate(this@AccountFragment.layoutInflater)
    }
    private val _parentViewModel: BottomNavigationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _parentViewModel.updateIndex(fragmentIndex)

        return _binding.root
    }
}