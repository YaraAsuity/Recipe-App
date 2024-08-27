package com.yara.recipeapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.yara.recipeapp.data.local.dao.FavouriteDao

class FavouritesViewModel(private val favoriteDao: FavouriteDao) : ViewModel() {

    val favorites = liveData {
        emitSource(favoriteDao.getAllFavorites())
    }
}

class FavouritesViewModelFactory(private val favoriteDao: FavouriteDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouritesViewModel(favoriteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
