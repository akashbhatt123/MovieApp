package com.example.movieapp.ui.movielist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.ApiService
import com.example.movieapp.data.remote.response.Movie
import com.example.movieapp.data.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val apiService: ApiService
): ViewModel() {
    private val _moviePosters = MutableLiveData<List<Movie>>()
    val moviePosters: LiveData<List<Movie>> get() = _moviePosters

    init {
        fetchMoviePosters()
    }

    fun fetchMoviePosters() {
        viewModelScope.launch {
            val moviesList = MoviesRepo(apiService).fetchPopularPosts()
            _moviePosters.postValue(moviesList)
        }
    }
}

