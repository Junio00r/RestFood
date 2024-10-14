package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.distinctUntilChanged
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.ActivityBottomNavigationBinding
import com.devmobile.android.restaurant.viewmodel.BottomNavigationViewModel
import java.util.LinkedList

class BottomNavigationActivity : AppCompatActivity() {

    private val _binding: ActivityBottomNavigationBinding by lazy {
        ActivityBottomNavigationBinding.inflate(layoutInflater)
    }
    private val _viewModel: BottomNavigationViewModel by viewModels {
        BottomNavigationViewModel.provideFactory(this@BottomNavigationActivity, null)
    }
    private val _navigationMenuStack: LinkedList<Int> = LinkedList(emptyList())

    // data
    private val homeFragment = HomeFragment().apply { arguments = Bundle().apply { putInt("FRAGMENT_INDEX", 0) } }
    private val qrCodeFragment = QrCodeFragment().apply { arguments = Bundle().apply { putInt("FRAGMENT_INDEX", 1) } }
    private val mapFragment = MapFragment().apply { arguments = Bundle().apply { putInt("FRAGMENT_INDEX", 2) } }
    private val accountFragment = AccountFragment().apply { arguments = Bundle().apply { putInt("FRAGMENT_INDEX", 3) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {

            startFragment(homeFragment, HomeFragment.FRAGMENT_TAG)
        }
        subscribeObservers()
        setContentView(_binding.root)
    }

    private fun subscribeObservers() {

        _binding.bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.menu_screen_1 -> {

                    startFragment(homeFragment, HomeFragment.FRAGMENT_TAG)
                    _viewModel.updateIndex(_binding.bottomNavigationView.menu.children.indexOf(item))
                    true
                }

                R.id.menu_screen_2 -> {

                    startFragment(qrCodeFragment, QrCodeFragment.FRAGMENT_TAG)
                    _viewModel.updateIndex(_binding.bottomNavigationView.menu.children.indexOf(item))
                    true
                }

                R.id.menu_screen_3 -> {

                    startFragment(mapFragment, MapFragment.FRAGMENT_TAG)
                    _viewModel.updateIndex(_binding.bottomNavigationView.menu.children.indexOf(item))
                    true
                }

                R.id.menu_screen_4 -> {

                    startFragment(accountFragment, AccountFragment.FRAGMENT_TAG)
                    _viewModel.updateIndex(_binding.bottomNavigationView.menu.children.indexOf(item))
                    true
                }

                else -> {

                    false
                }
            }

        }

        _viewModel.indexFragment.distinctUntilChanged()
            .observe(this@BottomNavigationActivity) { index ->

                _binding.bottomNavigationView.menu[index].isChecked = true
            }

        supportFragmentManager.addOnBackStackChangedListener {

            if (supportFragmentManager.fragments.isEmpty() && _navigationMenuStack.size == 0) {
                finish()
            }
        }
    }

    private fun <F : Fragment> startFragment(fragment: F, tag: String) {

        if (supportFragmentManager.backStackEntryCount > 0) {

            if (supportFragmentManager.fragments[supportFragmentManager.fragments.lastIndex].tag != tag) {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, fragment, tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN)
                    .setReorderingAllowed(true).addToBackStack(tag).commit()
            }

        } else {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN)
                .setReorderingAllowed(true).addToBackStack(tag).commit()
        }
    }
}