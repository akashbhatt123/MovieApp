package com.example.movieapp.ui.moviedetails

import Resource
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.ui.MainActivity
import com.example.movieapp.ui.moviedetails.adapter.ReviewsAdapter
import com.example.movieapp.ui.moviedetails.adapter.TrailersAdapter
import com.example.movieapp.ui.moviedetails.viewmodel.MovieDetailsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var trailersAdapter: TrailersAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("movieId") ?: 0

        val trailersRecyclerView: RecyclerView = view.findViewById(R.id.trailers_recycler_view)
        val reviewsRecyclerView: RecyclerView = view.findViewById(R.id.reviews_recycler_view)
        val movieBackDrop: ImageView = view.findViewById(R.id.movie_poster)
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
        val movieSynopsis: TextView = view.findViewById(R.id.movie_synopsis)
        val isLiked: FloatingActionButton = view.findViewById(R.id.fab_heart)
        val backBtn: ImageButton = view.findViewById(R.id.back_button)

        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        trailersRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        reviewsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        reviewsAdapter = ReviewsAdapter()
        trailersAdapter = TrailersAdapter()

        trailersRecyclerView.adapter = trailersAdapter
        reviewsRecyclerView.adapter = reviewsAdapter

        movieDetailsViewModel.loadData(movieId)
        movieDetailsViewModel.movieDetails.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { details ->
                        Glide.with(movieBackDrop.context)
                            .load("https://image.tmdb.org/t/p/w500${details.backdropPath}")
                            .placeholder(R.drawable.logo)
                            .into(movieBackDrop)

                        movieTitle.text = details.title
                        movieSynopsis.text = details.overview
                    }
                }

                else -> {}
            }
        }

        movieDetailsViewModel.movieTrailer.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.d("Check", "Trailer Success")
                    trailersAdapter.updateData(resource.data ?: emptyList())
                }

                else -> {
                    Log.d("Check", "Trailer Fail")

                }
            }
        }

        movieDetailsViewModel.movieReview.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    reviewsAdapter.updateData(resource.data ?: emptyList())
                }

                else -> {}
            }
        }

        movieDetailsViewModel.isFav(movieId).observe(viewLifecycleOwner) { isFav ->
            isFav?.let {
                isLiked.setImageResource(
                    if (isFav) {
                        R.drawable.ic_heart_red
                    } else {
                        R.drawable.ic_heart
                    }
                )
            }
        }

        isLiked.setOnClickListener {
            movieDetailsViewModel.toggleLike(movieId)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.hideToolBar()
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.showToolBar()
    }
}