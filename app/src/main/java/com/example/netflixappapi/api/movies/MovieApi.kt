package com.example.netflixappapi.api.movies

import retrofit2.Call
import retrofit2.http.GET

interface MovieApi {
    @GET("search?vtype=movie")
    fun getMovies(): Call<List<Movie>>

    @GET("search?vtype=series")
    fun getSeries(): Call<List<Movie>>
}