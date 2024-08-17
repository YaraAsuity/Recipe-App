package com.yara.recipeapp.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.yara.recipeapp.R
import com.yara.recipeapp.data.SharedPrefs
import com.yara.recipeapp.ui.user_data.login.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var loginFragment: LoginFragment
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager
        loginFragment = LoginFragment() // Assuming you need to use it later

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? NavHostFragment
        if (navHostFragment == null) {
            Log.e("MainActivity", "NavHostFragment is null!")
            finish()  // Or handle this scenario appropriately
        } else {
            navController = navHostFragment.navController
        }

        SharedPrefs.init(this)
    }
}