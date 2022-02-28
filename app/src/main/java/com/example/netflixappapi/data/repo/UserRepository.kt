package com.example.netflixappapi.data.repo

import com.example.netflixappapi.data.model.User
import com.example.netflixappapi.data.model.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(user : User) {
        userDao.addUser(user)
    }

    fun getAllUsers() : List<User> {
        return userDao.getAllUsers()
    }

    fun getUser(email : String) : User {
        return userDao.getUser(email)
    }
}