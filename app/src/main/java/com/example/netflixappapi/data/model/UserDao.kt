package com.example.netflixappapi.data.model

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT password FROM user_table WHERE email = :email")
    fun getUserPassword(email : String) : String
}