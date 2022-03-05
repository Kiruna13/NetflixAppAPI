package com.example.netflixappapi.api.movies_series

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import retrofit2.*

class NetflixRepo(private val netflixApi: NetflixApi) {
    var movies: MutableLiveData<Netflix> = MutableLiveData<Netflix>()
    var series: MutableLiveData<Netflix> = MutableLiveData<Netflix>()

    fun getMovies() {
        netflixApi.getMovies().enqueue(object : Callback<Netflix> {
            override fun onFailure(call: Call<Netflix>, t: Throwable) {
                println(t.localizedMessage)
            }

            override fun onResponse(call: Call<Netflix>, response: Response<Netflix>) {
                val gson = Gson().toJson(response.body())
                val res: Netflix = Gson().fromJson(gson, Netflix::class.java)
                movies.value = res
            }
        })
    }

    fun getSeries() {
        netflixApi.getSeries().enqueue(object : Callback<Netflix> {
            override fun onFailure(call: Call<Netflix>, t: Throwable) {
                println(t.localizedMessage)
            }

            override fun onResponse(call: Call<Netflix>, response: Response<Netflix>) {
                val gson = Gson().toJson(response.body())
                val res: Netflix = Gson().fromJson(gson, Netflix::class.java)
                series.value = res
            }
        })
    }
}