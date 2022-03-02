package com.example.netflixappapi

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.netflixappapi.ui.authentication.Authentication

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun registerUserTest() {
        //Arrange
        var email = "issou@chankla.com"
        var pass = "Issou69"
        var authentication = Authentication()

        //Act
        authentication.registerUser("","");
        //Assert
//
    }
}