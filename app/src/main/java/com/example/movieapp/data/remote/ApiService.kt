package com.example.movieapp.data.remote

import com.example.movieapp.data.remote.response.MovieDetails
import com.example.movieapp.data.remote.response.MovieReviews
import com.example.movieapp.data.remote.response.MoviesData
import com.example.movieapp.data.remote.response.MoviesTrailer
import com.example.movieapp.data.remote.response.Review
import com.example.movieapp.data.remote.response.Trailer
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

    @GET("{id}/videos")
    suspend fun getTrailerList(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<MoviesTrailer>

    @GET("{id}/reviews")
    suspend fun getMovieReviews(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieReviews>

    @GET("{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetails>
}
