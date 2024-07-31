package com.example.movieapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserLikes(
    @PrimaryKey
    val id: Int,
    val isLiked: Boolean = false
)
