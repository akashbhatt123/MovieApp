package com.example.movieapp.data.repo

import Resource
import com.example.movieapp.data.local.UserDao
import com.example.movieapp.data.local.UserLikes
import com.example.movieapp.data.remote.ApiService
import com.example.movieapp.data.remote.response.MovieDetails
import com.example.movieapp.data.remote.response.MoviesData
import com.example.movieapp.data.remote.response.Review
import com.example.movieapp.data.remote.response.Trailer
import com.example.movieapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class MoviesRepo @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    suspend fun fetchMovies(category: String, page: Int): Resource<MoviesData> {
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

    suspend fun fetchMovieTrailers(id: Int): Resource<List<Trailer>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTrailerList(id = id, apiKey = Constants.API_KEY)
                if (response.isSuccessful) {

                    Resource.Success(response.body()?.results ?: emptyList())
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

    suspend fun fetchMovieReviews(id: Int): Resource<List<Review>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getMovieReviews(
                    id = id, apiKey = Constants.API_KEY
                )

                if (response.isSuccessful) {
                    Resource.Success(response.body()?.results ?: emptyList())
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

    suspend fun fetchMovieDetails(id: Int): Resource<MovieDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getMovieDetails(
                    id = id, apiKey = Constants.API_KEY
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

    suspend fun insertMovies(movieId: Int) = userDao.insertMovies(UserLikes(movieId))

    suspend fun toggleIsLiked(id: Int) {
        userDao.toggleIsLiked(id)
    }

    fun isFav(movieId: Int) = userDao.getIsFav(movieId)
}