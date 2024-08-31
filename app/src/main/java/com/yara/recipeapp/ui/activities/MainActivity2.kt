package com.yara.recipeapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.yara.recipeapp.R
import com.yara.recipeapp.data.SharedPrefs
import com.yara.recipeapp.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var navController: NavController
    private var _binding: ActivityMain2Binding? = null
    private val binding get() = _binding!!
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        NavigationUI.setupWithNavController(toolbar, navController)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.navigation_search -> {
                    navController.navigate(R.id.searchFragment)
                    true
                }
                R.id.navigation_favourite -> {
                    navController.navigate(R.id.favouriteFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out -> {
                showSignOutDialog()
                true
            }
            else -> {
                item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showSignOutDialog() {
        navController.navigate(R.id.signOutDialogFragment)
    }

     fun signOut() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("login", true)
        }
        startActivity(intent)
        finishAffinity()
        SharedPrefs.clearLoginStatus()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}