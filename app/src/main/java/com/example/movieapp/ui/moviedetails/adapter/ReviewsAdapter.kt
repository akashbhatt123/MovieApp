package com.example.movieapp.ui.moviedetails.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.data.remote.response.Review

class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {
    private var reviews = listOf<Review>()

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val userReview: TextView = view.findViewById(R.id.user_review)
        fun setData(review: Review) {
            val reviewText = review.content
            userReview.text = reviewText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.review_list_items, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.setData(reviews[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: List<Review>) {
        reviews = data
        notifyDataSetChanged()
    }
}