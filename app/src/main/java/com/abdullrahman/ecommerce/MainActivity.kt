package com.abdullrahman.ecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.abdullrahman.ecommerce.domain.Constants
import com.abdullrahman.ecommerce.presentation.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var  viewModel:AuthViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

//        WindowCompat.setDecorFitsSystemWindows(window, false)
        viewModel.CustomerCheck().observe(this, Observer {

            if (it.isNotEmpty())
            {
                Constants.id = it[0].id!!
               // navController.navigate(R.id.action_signInFragment_to_buttomNavFragment2)

                navGraph.setStartDestination(R.id.buttomNavFragment)
                navController.graph = navGraph
                viewModel.isloading.value = false
                viewModel.isSignedIn.value = true

            }
            else{
                viewModel.isloading.value = false

            }
        })



    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    fun setupActionBar(toolBar: Toolbar) {

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false);

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController)

    }
}