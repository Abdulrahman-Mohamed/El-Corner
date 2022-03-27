package com.abdullrahman.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        WindowCompat.setDecorFitsSystemWindows(window, false)



    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    fun setupActionBar(toolBar: Toolbar) {

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController)

    }
}