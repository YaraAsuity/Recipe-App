package com.yara.recipeapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.yara.recipeapp.R
import com.yara.recipeapp.ViewModel.DetailsViewModel
import com.yara.recipeapp.ViewModel.DetailsViewModelFactory
import com.yara.recipeapp.data.remote.retrofit.Retrofit1
import com.yara.recipeapp.data.repository.DetailsRepo
import com.yara.recipeapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    val repository = DetailsRepo(Retrofit1.retrofit)
    private val viewModel : DetailsViewModel by viewModels(factoryProducer = { DetailsViewModelFactory(repository) })

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
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
        val mealId = arguments?.let { DetailsFragmentArgs.fromBundle(it).id }
        viewModel.mealDetails.observe(viewLifecycleOwner) { mealResponse ->
            val meal = mealResponse.meals[0]
            binding.apply {
                load.visibility = View.INVISIBLE
                TvMealName.text = meal.strMeal
                textViewMealCategory.text = "Category: ${meal.strCategory}"
                textViewMealArea.text = "Area: ${meal.strArea}"
                details.text= meal.strInstructions
            }
            binding.youtubeIcon.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data= Uri.parse(meal.strYoutube)
                startActivity(intent)
            }

            Glide.with(this)
                .load(meal.strMealThumb)
                .placeholder(R.drawable.meal)
                .error(R.drawable.error)
                .into(binding.imageMeal)
    }
        viewModel.fetchMealById(mealId.toString())
    }
}