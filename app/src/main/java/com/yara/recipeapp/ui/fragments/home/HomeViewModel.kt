package com.yara.recipeapp.ui.fragments.home

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

    private val _randomMeal = MutableLiveData<Meal>()
    val randomMeal: LiveData<Meal> get() = _randomMeal

    fun fetchRandomMeal() {
        viewModelScope.launch {
            try {
                val randomMeal = mealRepository.getRandomMeal()
                _randomMeal.postValue(randomMeal[0])
            } catch (e: Exception) {
                e.message
            }
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