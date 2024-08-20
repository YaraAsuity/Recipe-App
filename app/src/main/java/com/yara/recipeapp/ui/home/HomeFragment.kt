package com.yara.recipeapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yara.recipeapp.R
import com.yara.recipeapp.ViewModel.HomeViewModel
import com.yara.recipeapp.ViewModel.MealViewModelFactory
import com.yara.recipeapp.data.remote.retrofit.Retrofit1
import com.yara.recipeapp.data.repository.HomeRepo
import com.yara.recipeapp.databinding.FragmentHomeBinding
import com.yara.recipeapp.ui.adapters.AdapterHome

class HomeFragment : Fragment() {
    private val repository = HomeRepo(Retrofit1.retrofit)
    private val viewModel :HomeViewModel by viewModels(factoryProducer = { MealViewModelFactory(repository) })
    private var _binding: FragmentHomeBinding?= null
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
        binding.menu.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.inflate(R.menu.option_menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                // Handle menu item clicks here
                true
            }
            popupMenu.show()
        }
    }
}