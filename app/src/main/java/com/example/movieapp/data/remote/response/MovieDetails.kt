package com.example.movieapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("title")
    val title: String?
)