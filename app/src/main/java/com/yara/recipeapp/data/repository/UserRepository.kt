package com.yara.recipeapp.data.repository

import androidx.lifecycle.LiveData
import com.yara.recipeapp.data.db.User
import com.yara.recipeapp.data.db.UserDao

class UserRepository(private val userDao: UserDao) {


    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
    suspend fun getPassword(userMail:String):String{
       return userDao.readPassword(userMail)
    }
    suspend fun getUser(userMail:String ):User?{
        return userDao.readAllData(userMail)
    }


}