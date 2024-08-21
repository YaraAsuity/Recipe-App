package com.yara.recipeapp.data.repository

import com.yara.recipeapp.data.remote.models.Meal
import com.yara.recipeapp.data.remote.service.RecipeServices

class SearchRepo(private val mealApiService: RecipeServices) {
    suspend fun searchRecipes(query: String): List<Meal> {
        return mealApiService.searchRecipes(query).meals ?: emptyList()
    }
}