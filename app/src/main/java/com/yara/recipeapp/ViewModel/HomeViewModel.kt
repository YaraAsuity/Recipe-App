package com.yara.recipeapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yara.recipeapp.data.remote.models.Meal
import com.yara.recipeapp.data.repository.HomeRepo
import kotlinx.coroutines.launch

class HomeViewModel(private val mealRepository: HomeRepo) : ViewModel() {
    private val _mealsList = MutableLiveData<List<Meal>>()
    val mealsList : LiveData<List<Meal>> get()  = _mealsList
    fun fetchMealsByFirstLetter(letter: String) {
        viewModelScope.launch {
            val mealsList = mealRepository.getMealsByFirstLetter(letter)
            _mealsList.value = mealsList ?: emptyList()
        }
    }
}
class MealViewModelFactory(private val repository: HomeRepo) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}