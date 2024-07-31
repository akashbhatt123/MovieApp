package com.example.movieapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserLikes(
    @PrimaryKey
    val id: Int,
    val isLiked: Boolean = false
)
