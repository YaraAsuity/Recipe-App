package com.yara.recipeapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yara.recipeapp.data.local.entities.FavouriteEntity

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavouriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavouriteEntity)

    @Query("SELECT * FROM favorites WHERE mealId = :mealId")
    suspend fun getFavoriteById(mealId: String): FavouriteEntity?

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<FavouriteEntity>>
}

