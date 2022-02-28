package com.example.netflixappapi.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixappapi.data.AppDatabase
import com.example.netflixappapi.data.model.User
import com.example.netflixappapi.data.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : UserRepository

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun addUser(user : User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun getAllUsers() : List<User> {
        return repository.getAllUsers()
    }

    fun getUser(email : String) : User {
        return repository.getUser(email)
    }
}