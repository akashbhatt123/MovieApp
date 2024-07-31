package com.example.movieapp.ui.movielist.viewmodel

import Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.response.Movie
import com.example.movieapp.data.repo.MoviesRepo
import com.example.movieapp.ui.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
): ViewModel() {
    private val _moviePosters = MutableLiveData<Resource<List<Movie>>>()
    val moviePosters: LiveData<Resource<List<Movie>>> get() = _moviePosters
    private var currentPage: Int = 1
    private var totalPages: Int = 1
    private val _selectedCategory = MutableLiveData(MainActivity.POPULAR)
    val selectedCategory: LiveData<String> = _selectedCategory

    init {
        fetchMovies()
    }

    fun setSelectedCategory(category: String) {
        if(category != selectedCategory.value) _selectedCategory.postValue(category)
    }

    fun fetchMovies(currPage: Int = 1) {
        currentPage = currPage
        if(currentPage == 1) {
            _moviePosters.postValue(Resource.Loading())
        }

        if(currentPage > totalPages) return

        viewModelScope.launch {
            val response = moviesRepo.fetchMovies(_selectedCategory.value ?: "popular", currentPage)
            when(response) {
                is Resource.Success -> {
                    val movieResponse = response.data
                    movieResponse?.let {
                        val moviesList = it.results ?: emptyList()
                        if(currentPage == 1) {
                            _moviePosters.postValue(Resource.Success(moviesList))
                        } else {
                            val loadedMovies =
                                (_moviePosters.value as? Resource.Success)?.data ?: emptyList()
                            _moviePosters.postValue(Resource.Success(loadedMovies + moviesList))
                        }
                        totalPages = it.totalPages ?: 1
                    }
                }
                is Resource.Error -> {
                    if(currentPage == 1) {
                        _moviePosters.value = Resource.Error("API Call Failed")
                    }
                }
                else -> {
                    if(currentPage == 1) {
                        _moviePosters.postValue(Resource.Loading())
                    }
                }
            }
        }
    }

    fun loadMore() {
        if(!hasMorePages()) return
        currentPage++
        fetchMovies(currentPage)
    }

    private fun hasMorePages(): Boolean {
        return currentPage <= totalPages
    }
}
