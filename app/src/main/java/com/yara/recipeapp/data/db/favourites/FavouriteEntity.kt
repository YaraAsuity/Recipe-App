package com.yara.recipeapp.data.db.favourites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey val idMeal: Int,
    val strMeal: String,
    val strMealThumb: String
)