package com.example.netflixappapi.api

import com.example.netflixappapi.api.movies_series.NetflixApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Request


class RetrofitBuilder {

    object ApiClient {
        private const val BASE_URL: String = "https://unogsng.p.rapidapi.com/"

        private val gson : Gson by lazy {
            GsonBuilder().setLenient().create()
        }

        private val httpClient : OkHttpClient by lazy {
            OkHttpClient.Builder().addInterceptor { chain ->
                val request: Request =
                    chain.request().newBuilder()
                        .header("x-rapidapi-host", "unogsng.p.rapidapi.com")
                        .header("x-rapidapi-key", "ee05c3eec5mshd276e0fb4824c31p1856d2jsne5eb5c67bd72")
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

        val apiService : NetflixApi by lazy{
            retrofit.create(NetflixApi::class.java)
        }
    }

//    companion object{
//        val BASE_URL = "https://unogsng.p.rapidapi.com/"
//
//
//        fun createRetrofit(): Retrofit {
//
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//            return retrofit
//        }
//    }
}