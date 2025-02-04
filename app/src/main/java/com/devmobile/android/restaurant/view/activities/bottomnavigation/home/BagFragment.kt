package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.devmobile.android.restaurant.databinding.FragmentBagBinding
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.BagViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BagFragment : Fragment() {

    private val _binding: FragmentBagBinding by lazy {
        FragmentBagBinding.inflate(layoutInflater)
    }
    private val _viewModel: BagViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setUpObservables()

        return _binding.root
    }

    private fun setUpObservables() {

        lifecycleScope.launch(Dispatchers.Default) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                _viewModel.itemsOnBag.collect { bagItem ->
                    Log.d("DEBUGGING", "COLETOU $bagItem")
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
    }
}