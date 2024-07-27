package com.example.movieapp.data.remote

import com.example.movieapp.data.Resource
import com.example.movieapp.data.remote.response.MoviesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String = "f7f33e0742e930642aded858ac5300b8",
        @Query("page") page: Int = 1
    ): Response<MoviesData>

    @GET("top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String = "f7f33e0742e930642aded858ac5300b8",
        @Query("page") page: Int = 1
    ): Response<MoviesData>
}
