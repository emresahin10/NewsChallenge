package com.denbase.newschallenge.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.denbase.newschallenge.R
import com.denbase.newschallenge.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import github.com.st235.lib_expandablebottombar.MenuItemDescriptor
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomBar: ExpandableBottomBar = binding.expandableBottomBar
        val menu = bottomBar.menu
        navController = findNavController(R.id.fragment)
        setupActionBarWithNavController(navController)
        ExpandableBottomBarNavigationUI.setupWithNavController(bottomBar, navController)


        //Bottom menu create
        menu.add(
            MenuItemDescriptor.Builder(
                this,
                R.id.newsSourcesFragment,
                R.drawable.ic_sources,
                R.string.sources,
                Color.WHITE
            )
                .build()
        )

        menu.add(
            MenuItemDescriptor.Builder(
                this,
                R.id.newsSavedFragment,
                R.drawable.ic_save,
                R.string.save,
                Color.WHITE
            )
                .build()
        )

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}