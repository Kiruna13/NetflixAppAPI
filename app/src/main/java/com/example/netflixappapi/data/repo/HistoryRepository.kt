package com.example.netflixappapi.data.repo

import com.example.netflixappapi.data.model.History
import com.example.netflixappapi.data.model.HistoryDao
import com.example.netflixappapi.data.model.User
import com.example.netflixappapi.data.model.UserDao

class HistoryRepository(private val historyDao: HistoryDao) {

    suspend fun addHistory(history: History) {
        historyDao.addHistory(history)
    }

}