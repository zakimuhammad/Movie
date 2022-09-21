package com.zaki.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_trending")
    fun getTrendingMovies(): Single<List<MovieTrendingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieTrendingEntity>)
}