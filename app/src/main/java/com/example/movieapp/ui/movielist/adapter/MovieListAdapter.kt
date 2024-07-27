package com.example.movieapp.ui.movielist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.remote.response.Movie

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    private var posters = listOf<Movie>()

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val moviePoster: ImageView = view.findViewById(R.id.moviePoster)
        fun setData(post: Movie) {
            Glide.with(moviePoster.context)
                .load("https://image.tmdb.org/t/p/w500${post.posterPath}")
                .placeholder(R.drawable.logo)
                .into(moviePoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_items, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = posters.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setData(posters[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Movie>) {
        posters = newData
        notifyDataSetChanged()
    }
}
