package com.yara.recipeapp.ui.user_data.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.yara.recipeapp.R
import com.yara.recipeapp.data.SharedPrefs
import com.yara.recipeapp.ui.activities.MainActivity2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lifecycleScope.launch (Dispatchers.Main) {
            delay(4000)
            if(SharedPrefs.getCurrentUser() == -1)
            {
                Navigation.findNavController(view)
                    .navigate(R.id.action_splashFragment_to_loginFragment)
            }
            else
            {
                val intent = Intent(activity, MainActivity2::class.java)
                startActivity(intent)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

}