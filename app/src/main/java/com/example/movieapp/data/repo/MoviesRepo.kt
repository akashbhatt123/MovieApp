package com.example.movieapp.data.repo

import Resource
import com.example.movieapp.data.remote.ApiService
import com.example.movieapp.data.remote.response.MovieReviews
import com.example.movieapp.data.remote.response.MoviesData
import com.example.movieapp.data.remote.response.MoviesTrailer
import com.example.movieapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class MoviesRepo @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchPopularPosts(category: String, page: Int): Resource<MoviesData> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getMovieList(
                    category = category, apiKey = Constants.API_KEY, page = page
                )
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("API Error: ${response.code()} ${response.message()}")
                }
            } catch (e: HttpException) {
                Resource.Error("Network Error: ${e.message()}")
            } catch (e: IOException) {
                Resource.Error("IO error: ${e.message}")
            } catch (e: Exception) {
                Resource.Error("Failed to fetch data: ${e.message}")
            }
        }
    }

    suspend fun fetchMovieTrailers(id: Int, videos: String = "videos"): Resource<MoviesTrailer> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTrailerList(
                    id = id, videos = videos, apiKey = Constants.API_KEY
                )
                if(response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("API Error: ${response.code()} ${response.message()}")
                }
            } catch (e: HttpException) {
                Resource.Error("Network Error: ${e.message()}")
            } catch (e: IOException) {
                Resource.Error("IO error: ${e.message}")
            } catch (e: Exception) {
                Resource.Error("Failed to fetch data: ${e.message}")
            }
        }
    }

    suspend fun fetchMovieReviews(id: Int, reviews: String = "reviews"): Resource<MovieReviews> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getMovieReviews(
                    id = id, reviews = reviews, apiKey = Constants.API_KEY
                )
                if(response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("API Error: ${response.code()} ${response.message()}")
                }
            } catch (e: HttpException) {
                Resource.Error("Network Error: ${e.message()}")
            } catch (e: IOException) {
                Resource.Error("IO error: ${e.message}")
            } catch (e: Exception) {
                Resource.Error("Failed to fetch data: ${e.message}")
            }
        }
    }
}