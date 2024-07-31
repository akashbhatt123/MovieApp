package com.example.movieapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(userLikes: UserLikes)

    @Query("UPDATE UserLikes SET isLiked = NOT isLiked WHERE id = :id")
    suspend fun toggleIsLiked(id: Int)

    @Query("SELECT isLiked FROM USERLIKES WHERE  id= :id")
    fun getIsFav(id: Int): LiveData<Boolean>
}