package com.example.netflixappapi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true)
    val historyId: Int,
    val research: String,
    val userHistoryId : Int
)