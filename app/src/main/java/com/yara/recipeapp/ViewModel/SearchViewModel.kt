package com.yara.recipeapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yara.recipeapp.data.remote.models.Meal
import com.yara.recipeapp.data.repository.HomeRepo
import com.yara.recipeapp.data.repository.SearchRepo
import kotlinx.coroutines.launch

class SearchViewModel(private val mealRepository: SearchRepo) : ViewModel() {
    private val _recipes = MutableLiveData<List<Meal>>()
    val recipes: LiveData<List<Meal>> = _recipes

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            _recipes.value = mealRepository.searchRecipes(query)
        }
    }
}

class SearchViewModelFactory(private val repository: SearchRepo) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}