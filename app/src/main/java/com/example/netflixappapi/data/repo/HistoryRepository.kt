package com.example.netflixappapi.data.repo

import com.example.netflixappapi.data.model.History
import com.example.netflixappapi.data.model.HistoryDao

class HistoryRepository(private val historyDao: HistoryDao) {

    suspend fun addHistory(history: History) {
        historyDao.addHistory(history)
    }

    fun getUserHistories(userId : Int, type : String): List<History> {
        return historyDao.getUserHistories(userId, type)
    }

}