package com.zaki.movieapp.data.local.dao

import androidx.room.*
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import io.reactivex.rxjava3.core.Observable

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_trending")
    fun getTrendingMovies(): Observable<List<MovieTrendingEntity>>

    @Query("SELECT * FROM movie_trending WHERE is_favorite = 1")
    fun getFavoriteMovies(): Observable<List<MovieTrendingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: MovieTrendingEntity)

    @Update
    suspend fun updateMovie(movie: MovieTrendingEntity)
}