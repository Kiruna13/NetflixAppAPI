package com.example.netflixappapi.api

import com.example.netflixappapi.api.movies.MovieApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.Request


class RetrofitBuilder {

    object ApiClient {
        private const val BASE_URL: String = "https://jsonplaceholder.typicode.com/"

        private val gson : Gson by lazy {
            GsonBuilder().setLenient().create()
        }

        private val httpClient : OkHttpClient by lazy {
            OkHttpClient.Builder().addInterceptor { chain ->
                val request: Request =
                    chain.request().newBuilder()
                        .addHeader("x-rapidapi-host", "unogsng.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "3413b72261msh1b0ed65e99e2073p18fef7jsndbbcf11261e9")
                        .build()
                chain.proceed(request)
            }.build()
        }

        private val retrofit : Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        val apiService : MovieApi by lazy{
            retrofit.create(MovieApi::class.java)
        }
    }
}