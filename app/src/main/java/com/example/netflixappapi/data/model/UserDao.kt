package com.example.netflixappapi.data.model

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers() : List<User>

    @Query("SELECT * FROM user_table WHERE email = :email")
    fun getUser(email : String) : User
}