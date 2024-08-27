package com.yara.recipeapp.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yara.recipeapp.databinding.FragmentFavouriteBinding
import com.yara.recipeapp.ui.favourites.adapter.FavouritesAdapter
import com.yara.recipeapp.ViewModel.FavouritesViewModel
import com.yara.recipeapp.ViewModel.FavouritesViewModelFactory
import com.yara.recipeapp.data.local.database.FavouriteDatabase
import kotlinx.coroutines.launch


class FavouriteFragment : Fragment() {


    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouritesViewModel by viewModels {
        FavouritesViewModelFactory(FavouriteDatabase.getDatabase(requireContext()).favoriteDao())
    }
    private val favoriteDao by lazy {
        FavouriteDatabase.getDatabase(requireContext()).favoriteDao()
    }

    private var isFavorite = false
    private lateinit var mealId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavouritesAdapter { favorite ->
            val action = FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(favorite.mealId.toInt())
            findNavController().navigate(action)
        }

        binding.recyclerViewFavourites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
//        binding.favourite.deleteIcon.setOnClickListener{
//            removeFromFavorites()
//        }
        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            adapter.submitList(favorites)
        }

    }

    private fun removeFromFavorites() {
        lifecycleScope.launch {
            favoriteDao.getFavoriteById(mealId)?.let {
                favoriteDao.deleteFavorite(it)
                isFavorite = false
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
