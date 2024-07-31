package com.example.movieapp.ui.movielist

import Resource
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.ui.moviedetails.MovieDetailsFragment
import com.example.movieapp.ui.movielist.adapter.MovieListAdapter
import com.example.movieapp.ui.movielist.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private lateinit var movieListAdapter: MovieListAdapter
    private val movieListViewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.movieListRecyclerView)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        movieListAdapter = MovieListAdapter(
            onMovieClick = { movieId ->
                onMovieClicked(movieId)
            },
            loadMore = {
                movieListViewModel.loadMore()
            }
        )
        recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieListAdapter
        }

        movieListViewModel.moviePosters.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    progressBar.isVisible = true
                    recyclerView.isVisible = false
                }

                is Resource.Success -> {
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                    resource.data?.let { newMovies ->
                        movieListAdapter.updateData(newMovies)
                        movieListAdapter.notifyDataSetChanged()
                    }
                }

                else -> {
                    progressBar.isVisible = false
                }
            }
        }

        movieListViewModel.selectedCategory.observe(viewLifecycleOwner) {
            movieListViewModel.fetchMovies()
        }
    }

    fun setCategory(category: String) {
        movieListViewModel.setSelectedCategory(category)
    }

    private fun onMovieClicked(movieId: Int) {
        val movieDetailsFragment = MovieDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt("movieId", movieId)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, movieDetailsFragment)
            .addToBackStack(null)
            .commit()
    }
}
