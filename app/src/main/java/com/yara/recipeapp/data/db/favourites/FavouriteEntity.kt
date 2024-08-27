package com.yara.recipeapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mealId: String,
    val mealName: String,

    val mealThumb: String,
)
