package com.yara.recipeapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_data WHERE email = :userMail ")
    suspend fun readAllData(userMail:String  ):User?


    @Query("SELECT password FROM user_data WHERE email = :userMail")
    suspend fun readPassword(userMail:String): String

    @Query("UPDATE user_data SET password = :newPassword WHERE id = :userId")
    suspend fun updatePassword(userId: Int, newPassword: String)
    @Update
    suspend fun updateUser(user: User)
}

