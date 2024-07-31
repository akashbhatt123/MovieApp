package com.example.movieapp.ui.moviedetails.viewmodel

import Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.response.MovieDetails
import com.example.movieapp.data.remote.response.Review
import com.example.movieapp.data.remote.response.Trailer
import com.example.movieapp.data.repo.MoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo
) : ViewModel() {
    private val _movieTrailers = MutableLiveData<Resource<List<Trailer>>>()
    val movieTrailer: LiveData<Resource<List<Trailer>>> get() = _movieTrailers

    private val _movieReviews = MutableLiveData<Resource<List<Review>>>()
    val movieReview: LiveData<Resource<List<Review>>> get() = _movieReviews

    private val _movieDetails = MutableLiveData<Resource<MovieDetails>>()
    val movieDetails: LiveData<Resource<MovieDetails>> get() = _movieDetails

    private fun fetchTrailers(movieId: Int) {
        _movieTrailers.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = moviesRepo.fetchMovieTrailers(movieId)
            _movieTrailers.postValue(response)
        }
    }

    private fun fetchReviews(movieId: Int) {
        _movieReviews.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = moviesRepo.fetchMovieReviews(movieId)
            _movieReviews.postValue(response)
        }
    }

    private fun fetchDetails(movieId: Int) {
        _movieDetails.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = moviesRepo.fetchMovieDetails(movieId)
            _movieDetails.postValue(response)
        }
    }

    fun loadData(movieId: Int) = viewModelScope.launch {
        moviesRepo.insertMovies(movieId)
        viewModelScope.async { fetchDetails(movieId) }
        viewModelScope.async { fetchTrailers(movieId) }
        viewModelScope.async { fetchReviews(movieId) }
    }

    fun toggleLike(movieId: Int) {
        viewModelScope.launch {
            moviesRepo.toggleIsLiked(movieId)
        }
    }


    fun isFav(movieId: Int) = moviesRepo.isFav(movieId)
}