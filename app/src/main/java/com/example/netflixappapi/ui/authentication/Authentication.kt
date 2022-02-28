package com.example.netflixappapi.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.netflixappapi.R
import com.example.netflixappapi.data.model.User
import com.example.netflixappapi.data.viewmodel.UserViewModel
import java.math.BigInteger
import java.security.MessageDigest

class Authentication : AppCompatActivity() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var emailField : EditText
    private lateinit var passwordField : EditText
    private lateinit var authBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        authBtn = findViewById(R.id.id_signIn_Button)
        emailField = findViewById(R.id.id_Email_textInputLayout)
        passwordField = findViewById(R.id.id_Password_textInputLayout)

        authBtn.setOnClickListener {
            onSignInRequest()
        }
    }

    private fun onSignInRequest() {
        val email = emailField.text.toString()
        val password = passwordField.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            for (user : User in mUserViewModel.getAllUsers()) {
                if (user.email == email) {
                    authenticateUser(email, password)
                    return
                }
            }
            registerUser(email, password)
        } else {
            Toast.makeText(this, "Veuillez saisir tous les champs", Toast.LENGTH_SHORT).show()
        }
    }

    private fun authenticateUser(email : String, password : String) {
        val dbUser = mUserViewModel.getUser(email)
        if (dbUser.password == toMd5(password)) {
            //nextActivity(dbUser)
            Toast.makeText(this, "Authentification réussie", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Authentification échouée : mauvais mot de passe", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email : String, password: String) {
        val user = User(0, email, toMd5(password))
        mUserViewModel.addUser(user)
        emailField.text.clear()
        passwordField.text.clear()
        Toast.makeText(this, "Inscription effectuée, veuillez maintenant vous connecter", Toast.LENGTH_SHORT).show()
    }

    private fun toMd5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    /*private fun nextActivity(dbUser: User) {
        val intent : Intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userId", dbUser.userId)
        startActivity(intent)
    }*/
}