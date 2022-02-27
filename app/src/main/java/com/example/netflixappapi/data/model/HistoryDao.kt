package com.example.netflixappapi.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistory(history: History)

    @Query("SELECT * from history_table WHERE userHistoryId = :userId")
    fun getUserHistories(userId : Int): List<History>
}