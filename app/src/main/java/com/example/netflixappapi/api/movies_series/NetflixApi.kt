package com.example.netflixappapi.api.movies_series

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface NetflixApi {
    @GET("search?type=movie")
    fun getMovies(): Call<Netflix>

    @GET("search?type=series")
    fun getSeries(): Call<Netflix>
}