package com.example.movieapp.data.remote

import com.example.movieapp.data.remote.response.MoviesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{category}")
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MoviesData>
}
