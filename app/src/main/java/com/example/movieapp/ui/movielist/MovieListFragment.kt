package com.example.movieapp.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.ui.movielist.adapter.MovieListAdapter
import com.example.movieapp.ui.movielist.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieListViewModel: MovieListViewModel by viewModels()
        val recyclerView: RecyclerView = view.findViewById(R.id.movieListRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        movieListAdapter = MovieListAdapter()
        recyclerView.adapter = movieListAdapter

        movieListViewModel.moviePosters.observe(viewLifecycleOwner) { posters ->
            movieListAdapter.updateData(posters)
        }

        movieListViewModel.fetchMoviePosters()
    }
}
