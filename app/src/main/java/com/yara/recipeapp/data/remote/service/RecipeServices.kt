package com.yara.recipeapp.data.remote.service

import com.yara.recipeapp.data.remote.models.ModelRandomRecipe
import com.yara.recipeapp.data.remote.models.ModelRecipe
import retrofit2.http.GET

interface RecipeServices {
    @GET("search.php")
    suspend fun getRecipes(@retrofit2.http.Query("f") firstLetter: String): ModelRecipe
    @GET("random.php")
    suspend fun getRandomRecipes(): ModelRandomRecipe
}