package com.example.netflixappapi.api.movies_series

import com.google.gson.annotations.SerializedName

data class Netflix(
    @SerializedName("results")
    var listResults: List<Results>
)

data class Results(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("synopsis")
    var synopsis: String? = null,
    @SerializedName("img")
    var img: String? = null,
    @SerializedName("clist")
    var countryList: String? = null,
)