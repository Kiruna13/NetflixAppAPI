package com.example.netflixappapi.api.movies_series

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.netflixappapi.api.RetrofitBuilder

class NetflixViewModel : ViewModel() {
    private val netflixRepository: NetflixRepo

    init {
        val netflixApi = RetrofitBuilder.ApiClient.apiService
        netflixRepository = NetflixRepo(netflixApi)
    }

    val movies: MutableLiveData<Netflix> = netflixRepository.movies
    val series: MutableLiveData<Netflix> = netflixRepository.series

    fun getMovies() {
        netflixRepository.getMovies()
    }

    fun getSeries() {
        netflixRepository.getSeries()
    }

}