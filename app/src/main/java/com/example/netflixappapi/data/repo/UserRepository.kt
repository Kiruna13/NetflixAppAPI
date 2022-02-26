package com.example.netflixappapi.data.repo

import com.example.netflixappapi.data.model.User
import com.example.netflixappapi.data.model.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(user : User) {
        userDao.addUser(user)
    }

}