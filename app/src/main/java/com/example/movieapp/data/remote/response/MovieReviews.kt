package com.example.movieapp.data.remote.response


import com.google.gson.annotations.SerializedName

data class MovieReviews(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Review?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)

data class Review(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("url")
    val url: String?
)