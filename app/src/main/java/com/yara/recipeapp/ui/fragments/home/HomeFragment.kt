package com.yara.recipeapp.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.yara.recipeapp.R
import com.yara.recipeapp.ViewModel.HomeViewModel
import com.yara.recipeapp.ViewModel.MealViewModelFactory
import com.yara.recipeapp.data.SharedPrefs
import com.yara.recipeapp.data.remote.retrofit.Retrofit1
import com.yara.recipeapp.data.repository.HomeRepo
import com.yara.recipeapp.databinding.FragmentHomeBinding
import com.yara.recipeapp.ui.activities.MainActivity
import com.yara.recipeapp.ui.adapters.AdapterHome
import com.yara.recipeapp.ui.details.DetailsFragment

class HomeFragment : Fragment() {
    private val repository = HomeRepo(Retrofit1.retrofit)
    private val viewModel :HomeViewModel by viewModels(factoryProducer = { MealViewModelFactory(repository) })
    private var _binding: FragmentHomeBinding?= null
    private lateinit var toolbar: Toolbar
    private val binding get() = _binding!!
    private val adapter : AdapterHome by lazy { AdapterHome(emptyList()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home , container , false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        spinnerHandling()
        optionMenuHandling()
       viewModel.mealsList.observe(viewLifecycleOwner){
           adapter.update(it)
       }
        binding.view1.adapter = adapter

        binding.imgSwitcher.inAnimation = android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in)
        binding.imgSwitcher.outAnimation = android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out)
        startImageSwitching()
        imageSwitcherHandling()
        itemClickHandling()

        setHasOptionsMenu(true)
        setupToolbar(view)

    }
    private fun spinnerHandling(){
        binding.chars.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = binding.chars.selectedItem?.toString()?.lowercase() ?: return
                viewModel.fetchMealsByFirstLetter(selectedItem)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                viewModel.fetchMealsByFirstLetter("a")
            }
        }
    }
    private fun optionMenuHandling(){
        binding.toolbar.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.inflate(R.menu.option_menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                // Handle menu item clicks here
                true
            }
            popupMenu.show()
        }
    }
    private fun startImageSwitching() {
        val runnable = object : Runnable {
            override fun run() {
                viewModel.fetchRandomMeal()
                Handler(Looper.getMainLooper()).postDelayed(this, 2000) // 2 seconds delay
            }
        }
        Handler(Looper.getMainLooper()).post(runnable)
    }
    private fun imageSwitcherHandling() {
        binding.imgSwitcher.setFactory {
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
        viewModel.randomMeal.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it.strMealThumb)
                .into(binding.imgSwitcher.currentView as ImageView)
        }
    }
    private fun itemClickHandling() {
        adapter.setOnItemClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it.idMeal.toInt()))
        }
    }
    private fun setupToolbar(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sign_out -> {
                SharedPrefs.signOut()
                activity?.let {
                    finishAffinity(it)
                    val intent = Intent(it, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
        }
}