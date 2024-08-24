package com.yara.recipeapp.data.repository

import com.yara.recipeapp.data.remote.models.ModelRecipe
import com.yara.recipeapp.data.remote.service.RecipeServices

class DetailsRepo (private val mealApiService: RecipeServices){

    suspend fun getMealById(mealId: String): ModelRecipe {
        return mealApiService.getRecipeById(mealId)
    }
}