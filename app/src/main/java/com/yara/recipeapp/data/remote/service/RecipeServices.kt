package com.yara.recipeapp.data.remote.service

import com.yara.recipeapp.data.remote.models.ModelRandomRecipe
import com.yara.recipeapp.data.remote.models.ModelRecipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeServices {
    @GET("search.php")
    suspend fun getRecipes(@retrofit2.http.Query("f") firstLetter: String): ModelRecipe
    @GET("random.php")
    suspend fun getRandomRecipes(): ModelRandomRecipe
    @GET("search.php")
    suspend fun searchRecipes(@Query("s") query: String): ModelRecipe
}