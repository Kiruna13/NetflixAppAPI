package com.example.netflixappapi.api.movies

data class Movie(
    // mettre les bonnes données qu'on souhaite récupérer
    val id: Int? = null,
    val name: String? = null,
    val userName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val website: String? = null
)