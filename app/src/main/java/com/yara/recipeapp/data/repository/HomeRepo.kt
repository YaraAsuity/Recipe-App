package com.yara.recipeapp.data.repository

import com.yara.recipeapp.data.remote.models.Meal
import com.yara.recipeapp.data.remote.service.RecipeServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepo(private val mealApiService: RecipeServices) {

    suspend fun getMealsByFirstLetter(letter: String): List<Meal>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = mealApiService.getRecipes(letter)
                response.meals // Return the list of meals
            } catch (e: Exception) {
                e.printStackTrace()
                null // Return null in case of an error
            }
        }
    }
    suspend fun getRandomMeal(): List<Meal> {
        return mealApiService.getRandomRecipes().meals
    }
}