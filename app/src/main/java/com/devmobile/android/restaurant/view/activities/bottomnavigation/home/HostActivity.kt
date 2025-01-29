package com.devmobile.android.restaurant.view.activities.bottomnavigation.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.devmobile.android.restaurant.databinding.ActivityItemsManagerBinding
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.repository.MenuManagerRemoteRepository
import com.devmobile.android.restaurant.model.datasource.remote.DatabaseSimulator
import com.devmobile.android.restaurant.usecase.entities.Bag
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.MenuManagerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HostActivity : AppCompatActivity() {

    private val binding: ActivityItemsManagerBinding by lazy {
        ActivityItemsManagerBinding.inflate(layoutInflater)
    }
    private val viewModel: MenuManagerViewModel by viewModels {
        MenuManagerViewModel.provideFactory(repository, this@HostActivity, restaurantId, Bag.getInstance())
    }
    private val repository: MenuManagerRemoteRepository by lazy {
        MenuManagerRemoteRepository.getInstance(
            restaurantDao = RestaurantLocalDatabase.getInstance(this).getRestaurantDao(),
            foodDao = RestaurantLocalDatabase.getInstance(this).getItemDao()
        )
    }

    // data
    private val restaurantId: Long by lazy {
        intent.getLongExtra("RESTAURANT_ID", 0)
    }

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(binding.navHostItems.id) as NavHostFragment).findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setUpWindowsResize()
        setContentView(binding.root)

        viewModel
        if (savedInstanceState == null) {
            createFakeRemoteDatabase()
        }
    }

    private fun setUpWindowsResize() {

        WindowCompat.setDecorFitsSystemWindows(this.window, false)
        ViewCompat.setWindowInsetsAnimationCallback(
            binding.root,
            object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {

                override fun onProgress(
                    insets: WindowInsetsCompat,
                    runningAnimations: MutableList<WindowInsetsAnimationCompat>
                ): WindowInsetsCompat {

                    val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
                    binding.root.updatePadding(bottom = ime.bottom)

                    return insets
                }
            }
        )
    }

//    private fun setUpObservables() {
//
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.CREATED) {
//                _viewModel.onItemSelected.collect {
//
//                    findNavController(R.id.nav_host_items).navigate(R.id.action_from_Menu_Fragment_to_Item_Selected)
////                    navController.navigate(R.id.action_from_Menu_Fragment_to_Item_Selected)
//                }
//            }
//
//            _viewModel.onAddItemToBag.observe(this@HostActivity) {
//
//            }
//        }
//    }

    private fun createFakeRemoteDatabase() {

        lifecycleScope.launch(Dispatchers.Default) {

            DatabaseSimulator.addRestaurants(this@HostActivity)
            DatabaseSimulator.addItemDataToDatabase(this@HostActivity)
        }
    }
}