package com.example.netflixappapi.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.netflixappapi.data.AppDatabase
import com.example.netflixappapi.data.model.History
import com.example.netflixappapi.data.repo.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : HistoryRepository

    init {
        val historyDao = AppDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(historyDao)
    }

    fun addHistory(history : History) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHistory(history)
        }
    }

    fun getUserHistories(userId : Int, type : String): List<History> {
        return repository.getUserHistories(userId, type)
    }

}