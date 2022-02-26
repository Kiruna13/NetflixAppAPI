package com.example.netflixappapi.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistory(history: History)

    @Query("SELECT history_table.research from history_table, user_table WHERE user_table.userId = :userId")
    fun getUserHistories(userId : Int) : List<History>

}