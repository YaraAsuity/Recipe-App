package com.yara.recipeapp.ui.fragments.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yara.recipeapp.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        // Find TextViews and set content
        val creatorTextView = view.findViewById<TextView>(R.id.creator_name)
        val synopsisTextView = view.findViewById<TextView>(R.id.app_synopsis)

        creatorTextView.text = "Created by: Team 2"
        synopsisTextView.text = "This app provides Recipes with thier Details and video."

        val backButton = view.findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return view
    }
}