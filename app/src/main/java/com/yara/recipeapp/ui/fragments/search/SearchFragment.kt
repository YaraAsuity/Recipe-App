package com.yara.recipeapp.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.yara.recipeapp.R
import com.yara.recipeapp.data.remote.retrofit.Retrofit1
import com.yara.recipeapp.data.repository.SearchRepo
import com.yara.recipeapp.databinding.FragmentSearchBinding
import com.yara.recipeapp.ui.adapters.AdapterHome

class SearchFragment: Fragment() {
    val repository = SearchRepo(Retrofit1.retrofit)
    private val viewModel : SearchViewModel by viewModels(factoryProducer = { SearchViewModelFactory(repository) })
    private var _binding: FragmentSearchBinding?= null
    private val binding get() = _binding!!
    private val adapter : AdapterHome by lazy { AdapterHome(emptyList()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search , container , false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        (activity as AppCompatActivity).setupActionBarWithNavController(navController, appBarConfiguration)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title=" "
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        binding.searchBtn.setOnClickListener {
            viewModel.searchRecipes(binding.search.text.toString())
        }
        viewModel.recipes.observe(viewLifecycleOwner){
            adapter.update(it)
        }
        binding.view1.adapter = adapter
        itemClickHandling()
    }

    private fun itemClickHandling(){
        adapter.setOnItemClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it.idMeal.toInt()))
        }
    }
}