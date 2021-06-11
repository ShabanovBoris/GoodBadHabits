package com.practice.goodbadhabits.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.practice.goodbadhabits.R
import com.practice.goodbadhabits.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
    }

    override fun onStart() {
        super.onStart()
        val drawerLayout: DrawerLayout = binding.drawerLayout

        navController = findNavController(R.id.nav_host_fragment_container)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        /**
         * Set up automatically change back/hamburger button in toolbar for drawer
         */
        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )
        binding.navViewDrawer.setNavigationItemSelectedListener {
            Toast.makeText(this, "navViewDrawer", Toast.LENGTH_SHORT).show()
            drawerLayout.close()
            true
        }

    }

    /**
     * Open the drawer menu
     */
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,appBarConfiguration)
    }
}