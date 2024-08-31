package com.yara.recipeapp.ui.fragments.details

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.yara.recipeapp.R
import com.yara.recipeapp.data.local.database.FavouriteDatabase
import com.yara.recipeapp.data.local.entities.FavouriteEntity
import com.yara.recipeapp.data.remote.models.Meal
import com.yara.recipeapp.data.remote.retrofit.Retrofit1
import com.yara.recipeapp.data.repository.DetailsRepo
import com.yara.recipeapp.databinding.FragmentDetailsBinding
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private val repository = DetailsRepo(Retrofit1.retrofit)
    private val viewModel: DetailsViewModel by viewModels(factoryProducer = { DetailsViewModelFactory(repository) })

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val favoriteDao by lazy {
        FavouriteDatabase.getDatabase(requireContext()).favoriteDao()
    }

    private var isFavorite = false
    private lateinit var mealId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)

        mealId = arguments?.let { DetailsFragmentArgs.fromBundle(it).id }.toString()
        checkIfFavorite(mealId)

        viewModel.mealDetails.observe(viewLifecycleOwner) { mealResponse ->
            val meal = mealResponse.meals[0]

            binding.apply {
                load.visibility = View.INVISIBLE
                TvMealName.text = meal.strMeal
                textViewMealCategory.text = "Category: ${meal.strCategory}"
                textViewMealArea.text = "Area: ${meal.strArea}"
                details.text = meal.strInstructions

                Glide.with(this@DetailsFragment)
                    .load(meal.strMealThumb)
                    .placeholder(R.drawable.meal)
                    .error(R.drawable.error)
                    .into(binding.imageMeal)
            }

            binding.youtubeIcon.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube)))
            }

            binding.Love.setOnClickListener {
                if (isFavorite) {
                    removeFromFavorites()
                } else {
                    addToFavorites(meal)
                }
            }
        }

        viewModel.fetchMealById(mealId)
    }

    private fun checkIfFavorite(mealId: String) {
        lifecycleScope.launch {
            val favorite = favoriteDao.getFavoriteById(mealId)
            isFavorite = favorite != null
            updateFavoriteIcon()
        }
    }

    private fun updateFavoriteIcon() {
        val favoriteColor = if (isFavorite) {
            ContextCompat.getColor(requireContext(), R.color.red) // Use color resource
        } else {
            ContextCompat.getColor(requireContext(), R.color.black) // Use color resource
        }

        binding.Love.setColorFilter(favoriteColor, PorterDuff.Mode.SRC_IN)
    }

    private fun addToFavorites(meal: Meal) {
        lifecycleScope.launch {
            val favorite = FavouriteEntity(
                mealId = mealId,
                mealName = meal.strMeal,

                mealThumb = meal.strMealThumb,
            )
            favoriteDao.insertFavorite(favorite)
            isFavorite = true
            updateFavoriteIcon()
        }
    }

    private fun removeFromFavorites() {
        lifecycleScope.launch {
            favoriteDao.getFavoriteById(mealId)?.let {
                favoriteDao.deleteFavorite(it)
                isFavorite = false
                updateFavoriteIcon()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
