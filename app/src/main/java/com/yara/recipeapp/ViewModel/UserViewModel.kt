package com.yara.recipeapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yara.recipeapp.data.db.User
import com.yara.recipeapp.data.db.UserDatabase
import com.yara.recipeapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application):AndroidViewModel(application ) {
    private val repository: UserRepository
    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }
    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }
    suspend fun getUser(userMail:String):User? {
        return withContext(Dispatchers.IO) {
          repository.getUser(userMail)
        }
    }

}