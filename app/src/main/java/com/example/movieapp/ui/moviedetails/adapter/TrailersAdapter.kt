package com.example.movieapp.ui.moviedetails.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.remote.response.Trailer

class TrailersAdapter : RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder>() {
    private var trailers: List<Trailer> = listOf()

    class TrailerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val thumbnailImage: ImageView = view.findViewById(R.id.youtube_thumbnail)

        fun setData(trailer: Trailer) {
            val trailerThumbnail = trailer.key?.let { "https://img.youtube.com/vi/${trailer.key}/0.jpg" }
            if (trailerThumbnail != null) {
                Glide.with(thumbnailImage.context)
                    .load(trailerThumbnail)
                    .placeholder(R.drawable.logo)
                    .into(thumbnailImage)
            } else {
                thumbnailImage.setImageResource(R.drawable.logo)
            }

            // Set an OnClickListener on the thumbnail
            itemView.setOnClickListener {
                trailer.key?.let { key ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$key"))
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trailer_list_items, parent, false)
        return TrailerViewHolder(view)
    }

    override fun getItemCount(): Int = trailers.size

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.setData(trailers[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<Trailer>) {
        trailers = data
        notifyDataSetChanged()
    }
}
