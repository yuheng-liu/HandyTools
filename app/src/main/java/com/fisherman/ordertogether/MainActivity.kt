package com.fisherman.ordertogether

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var navController: NavController? = null
    private var navigationView: NavigationView? = null
    private val snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() { // attaching view components to view variables
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)

        // toolbar setup
        setSupportActionBar(toolbar)

        // navController setup
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController!!, drawerLayout)
//        NavigationUI.setupWithNavController(navigationView, navController!!)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        drawerLayout!!.closeDrawers()
        val id = item.itemId
        //        switch (id) {
//            case R.id.navigation:
////                navController.navigate(R.id.navigationFragment);
//                break;
//            case R.id.view_model:
////                navController.navigate(R.id.viewModelFragment);
//                break;
//            case R.id.broadcast_receiver:
////                navController.navigate(R.id.broadcastReceiverFragment);
//                break;
//            case R.id.facebook_google:
////                navController.navigate(R.id.socialLoginFragment);
//                break;
//        }
        return true
    }
}