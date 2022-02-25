package com.example.netflixappapi.api.movies

import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {
    @GET("movies")
    fun getMovie(): Call<List<Movie>>
}