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
                response.meals
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    suspend fun getRandomMeal(): List<Meal> {
        return mealApiService.getRandomRecipes().meals
    }
}