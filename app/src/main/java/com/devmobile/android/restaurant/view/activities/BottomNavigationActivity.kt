package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.databinding.ActivityBottomNavigationBinding
import java.util.LinkedList

class BottomNavigationActivity : AppCompatActivity() {

    private val _binding: ActivityBottomNavigationBinding by lazy {
        ActivityBottomNavigationBinding.inflate(layoutInflater)
    }
    private val _navigationMenuStack: LinkedList<Int> = LinkedList(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {

            subscribeObservers()
            _navigationMenuStack.add(0)
            startFragment(HomeFragment.getInstance(), HomeFragment.FRAGMENT_TAG)
        }
        setContentView(_binding.root)
    }

    private fun subscribeObservers() {

        _binding.bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.menu_screen_1 -> {

                    startFragment(HomeFragment.getInstance(), HomeFragment.FRAGMENT_TAG)
                    _navigationMenuStack.add(0)
                    true
                }

                R.id.menu_screen_2 -> {

                    startFragment(QrCodeFragment.getInstance(), QrCodeFragment.FRAGMENT_TAG)
                    _navigationMenuStack.add(1)
                    true
                }

                R.id.menu_screen_3 -> {

                    startFragment(MapFragment.getInstance(), MapFragment.FRAGMENT_TAG)
                    _navigationMenuStack.add(2)
                    true
                }

                R.id.menu_screen_4 -> {

                    startFragment(AccountFragment.getInstance(), AccountFragment.FRAGMENT_TAG)
                    _navigationMenuStack.add(3)
                    true
                }

                else -> {
                    false
                }
            }

        }

        supportFragmentManager.addOnBackStackChangedListener {

            if (supportFragmentManager.fragments.isEmpty() && _navigationMenuStack.size == 0) {
                finish()
            } else if (
                _navigationMenuStack.size >= 1
                && supportFragmentManager.backStackEntryCount < _navigationMenuStack.size
            ) {

                _navigationMenuStack.removeLast()
                _binding.bottomNavigationView.menu[_navigationMenuStack.last()].isChecked = true
            }
        }
    }

    private inline fun <reified F : Fragment> startFragment(fragment: F, tag: String) {

        if (supportFragmentManager.backStackEntryCount > 0) {

            if (supportFragmentManager.fragments[supportFragmentManager.fragments.lastIndex].tag != tag) {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view, fragment, tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN)
                    .setReorderingAllowed(true)
                    .addToBackStack(tag)
                    .commit()
            } else {
                _navigationMenuStack.removeLast()
            }

        } else {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN)
                .setReorderingAllowed(true)
                .addToBackStack(tag)
                .commit()
        }
    }
}

