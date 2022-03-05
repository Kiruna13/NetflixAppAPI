package com.example.netflixappapi

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.netflixappapi.data.AppDatabase
import com.example.netflixappapi.data.model.User
import com.example.netflixappapi.data.model.UserDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AuthenticationTest : TestCase() {

    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java).allowMainThreadQueries().build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testRegisterUser_Equals() {
        runBlocking {
            val user = User(1, "testuser@test.fr", "testpassword123")
            userDao.addUser(user)
            val byEmail = userDao.getUser("testuser@test.fr")
            Assert.assertEquals(byEmail, user)
        }
    }

    @Test
    fun testRegisterUser_NotEquals() {
        runBlocking {
            val user = User(1, "testuser@test.fr", "testpassword123")
            userDao.addUser(user)
            val byEmail = userDao.getUser("testdavid@user.fr")
            Assert.assertNotEquals(user, byEmail)
        }
    }

    @Test
    fun testGetAllUsers_ReturnsListSizeSuperiorOrEqualToTwo() {
        runBlocking {
            val user1 = User(1, "testuser1@test.fr", "testpassword123")
            val user2 = User(2, "testuser2@test.fr", "testpassword123")
            userDao.addUser(user1)
            userDao.addUser(user2)
            val allUsers = userDao.getAllUsers()
            Assert.assertTrue(allUsers.size >= 2)
        }
    }
}