package com.yara.recipeapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yara.recipeapp.data.remote.models.ModelRecipe
import com.yara.recipeapp.data.repository.DetailsRepo
import kotlinx.coroutines.launch

class DetailsViewModel(private val mealRepository: DetailsRepo) : ViewModel() {

    private val _mealDetails = MutableLiveData<ModelRecipe>()
    val mealDetails: LiveData<ModelRecipe> get() = _mealDetails

    fun fetchMealById(mealId: String) {
        viewModelScope.launch {
            try {
                val list = mealRepository.getMealById(mealId)
                _mealDetails.postValue(list)
            } catch (e: Exception) {
                e.message
            }
        }
    }
}
class DetailsViewModelFactory(private val repository: DetailsRepo) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}