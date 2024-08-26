import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yara.recipeapp.data.db.favourites.RecipeDao
import com.yara.recipeapp.data.db.favourites.RecipeEntity
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {
    val favouriteRecipes: LiveData<List<RecipeEntity>> = recipeDao.getFavouriteRecipes() // Changed from favoriteRecipes to favouriteRecipes

    fun addFavourite(recipe: RecipeEntity) {
        viewModelScope.launch {
            recipeDao.insertFavourite(recipe) // Changed from insertFavorite to insertFavourite
        }
    }

    fun removeFavourite(recipe: RecipeEntity) {
        viewModelScope.launch {
            recipeDao.deleteFavourite(recipe) // Changed from deleteFavorite to deleteFavourite
        }
    }
}
