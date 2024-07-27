package com.example.movieapp.data.repo

import androidx.lifecycle.LiveData
import com.example.movieapp.data.remote.ApiService
import com.example.movieapp.data.remote.response.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MoviesRepo(private val apiService: ApiService) {
    suspend fun fetchPopularPosts(): List<Movie> {
        return withContext(Dispatchers.IO) {
            apiService.getPopular().body()?.results ?: emptyList()
        }
    }

    suspend fun fetchTopRatedPosts(): List<Movie> {
        return withContext(Dispatchers.IO) {
            apiService.getTopRated().body()?.results ?: emptyList()
        }
    }

//    fun observePosts(): LiveData<List<Movie>> {
//
//    }
}