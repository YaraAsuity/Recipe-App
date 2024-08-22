package com.yara.recipeapp.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yara.recipeapp.R
import com.yara.recipeapp.databinding.ActivityMain2Binding
import com.yara.recipeapp.databinding.FragmentHomeBinding
import com.yara.recipeapp.databinding.FragmentSearchBinding
import com.yara.recipeapp.ui.home.HomeFragment
import com.yara.recipeapp.ui.search.SearchFragment

class MainActivity2 : AppCompatActivity() {
    private var _binding: ActivityMain2Binding?= null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)?.findNavController()
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController?.navigate(R.id.homeFragment)
                    true
                }
                R.id.navigation_search -> {
                    navController?.navigate(R.id.searchFragment)
                    true
                }
                R.id.navigation_favourite -> {
                    //navController.navigate()
                    true
                }
                else -> false
            }
        }
    }
}