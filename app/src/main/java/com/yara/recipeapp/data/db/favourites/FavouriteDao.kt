package com.yara.recipeapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yara.recipeapp.data.db.favourites.FavouriteEntity

@Dao
interface FavouriteDao {
    @Insert
    suspend fun insert(favourite: FavouriteEntity)

    @Delete
    suspend fun delete(favourite: FavouriteEntity)

    @Query("SELECT * FROM favourites")
    fun getAllFavourites(): LiveData<List<FavouriteEntity>>

    @Query("SELECT COUNT(*) FROM favourites WHERE idMeal = :idMeal")
    fun isFavourite(idMeal: Int): LiveData<Int>
}
