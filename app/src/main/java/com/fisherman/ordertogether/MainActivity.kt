package com.fisherman.ordertogether

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        setupListeners()
    }

    private fun setupNavigation() { // attaching view components to view variables
        // toolbar setup
        setSupportActionBar(toolbar)    //
        supportActionBar?.setDisplayShowHomeEnabled(true)   // show the home button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)   // enable home button to become an up button

        // navController setup
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
    }

    private fun setupListeners() {
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                // TODO add more navigation destinations here
                else -> true
            }
        }
    }

    override fun onSupportNavigateUp() = NavigationUI.navigateUp(navController, drawerLayout)

    override fun onBackPressed() {
        when (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            true -> drawerLayout.closeDrawer(GravityCompat.START)
            false -> super.onBackPressed()
        }
    }
}