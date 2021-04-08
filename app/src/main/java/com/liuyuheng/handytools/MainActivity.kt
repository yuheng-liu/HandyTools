package com.liuyuheng.handytools

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.liuyuheng.handytools.databinding.ActivityMainBinding
import com.liuyuheng.handytools.network.retrofit.interceptor.ApiCallState
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainActivitySharedViewModel by viewModel<MainActivitySharedViewModel>()

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupListeners()
        setupObservers()
    }

    private fun setupNavigation() { // attaching view components to view variables
        // toolbar setup
        setSupportActionBar(binding.toolbar.toolbar)    // replace action bar with toolbar
        supportActionBar?.setDisplayShowHomeEnabled(true)   // show the home button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)   // enable home button to become an up button

        // navController setup
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navigationView, navController)
    }

    private fun setupListeners() {
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                // TODO add more navigation destinations here
                else -> true
            }
        }
    }

    private fun setupObservers() {
//        mainActivitySharedViewModel.getApiStateLiveData().observe(this) { apiCallState ->
//            when (apiCallState) {
//                ApiCallState.Idle -> Log.d("myDebug", "api call state: IDLE")
//                ApiCallState.Waiting -> Log.d("myDebug", "api call state: WAITING")
//                ApiCallState.Completed -> Log.d("myDebug", "api call state: COMPLETED")
//            }
//        }
    }

    override fun onSupportNavigateUp() = NavigationUI.navigateUp(navController, binding.drawerLayout)

    override fun onBackPressed() {
        when (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            true -> binding.drawerLayout.closeDrawer(GravityCompat.START)
            false -> super.onBackPressed()
        }
    }
}