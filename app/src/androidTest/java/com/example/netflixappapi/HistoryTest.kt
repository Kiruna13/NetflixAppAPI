package com.example.netflixappapi

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.netflixappapi.data.AppDatabase
import com.example.netflixappapi.data.model.History
import com.example.netflixappapi.data.model.HistoryDao
import com.example.netflixappapi.data.model.User
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class HistoryTest : TestCase() {

    private lateinit var historyDao: HistoryDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java).allowMainThreadQueries().build()
        historyDao = db.historyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testAddHistory_ReturnsTrue() {
        runBlocking {
            val previousHistoriesSize = historyDao.getUserHistories(1, "series").size
            val history = History(0, "cauchemar en cuisine", "series", 1)
            historyDao.addHistory(history)
            val newHistoriesSize = historyDao.getUserHistories(1, "series").size
            Assert.assertTrue(newHistoriesSize > previousHistoriesSize)
        }
    }

    @Test
    fun testGetHistories_ReturnsSeries() {
        runBlocking {
            var actual = true
            val expected = true
            val history1 = History(1, "cauchemar en cuisine", "series", 1)
            val history2 = History(2, "arcane", "series", 1)
            val history3 = History(3, "james bond", "film", 1)
            val history4 = History(4, "squid game", "series", 1)
            historyDao.addHistory(history1)
            historyDao.addHistory(history2)
            historyDao.addHistory(history3)
            historyDao.addHistory(history4)

            val histories = historyDao.getUserHistories(1, "series")
            for (history : History in histories) {
                if (history.type == "film") {
                    actual = false
                }
            }
            Assert.assertEquals(actual, expected)
        }
    }

    @Test
    fun testGetHistories_ReturnsFilms() {
        runBlocking {
            var actual = true
            val expected = true
            val history1 = History(1, "cauchemar en cuisine", "series", 1)
            val history2 = History(2, "arcane", "series", 1)
            val history3 = History(3, "james bond", "film", 1)
            val history4 = History(4, "squid game", "series", 1)
            historyDao.addHistory(history1)
            historyDao.addHistory(history2)
            historyDao.addHistory(history3)
            historyDao.addHistory(history4)

            val histories = historyDao.getUserHistories(1, "films")
            for (history : History in histories) {
                if (history.type == "series") {
                    actual = false
                }
            }
            Assert.assertEquals(actual, expected)
        }
    }
}