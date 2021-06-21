package com.practice.goodbadhabits.ui

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
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
            Toast.makeText(this, "${it.title}", Toast.LENGTH_SHORT).show()
            drawerLayout.close()
            true
        }
        /**
         * set the avatar
         */
        Glide.with(this)
            .load("https://i.pinimg.com/736x/bf/18/4c/bf184c45e4b9dc385010052ee121c19f.jpg")
            .transform(CircleCrop())
            .placeholder(R.drawable.preload_placeholder)
            .error(R.drawable.error_placeholder)
            .into(binding.navViewDrawer.getHeaderView(0).findViewById(R.id.iv_avatar))
    }


    /**
     * Open the drawer menu
     */
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}