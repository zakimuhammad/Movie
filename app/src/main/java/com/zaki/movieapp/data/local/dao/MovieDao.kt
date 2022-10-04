package com.zaki.movieapp.data.local.dao

import androidx.room.*
import com.zaki.movieapp.data.local.entitiy.MovieFavoriteEntity
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import io.reactivex.rxjava3.core.Observable

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_trending")
    fun getTrendingMovies(): Observable<List<MovieTrendingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: MovieTrendingEntity)

    @Query("SELECT * FROM movie_favorites")
    fun getFavoriteMovies(): Observable<List<MovieFavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieFavoriteEntity: MovieFavoriteEntity)

    @Delete
    suspend fun deleteMovie(movieFavoriteEntity: MovieFavoriteEntity)

    @Query("SELECT * FROM movie_favorites WHERE id = :movieId")
    fun getFavoriteMovie(movieId: Int): Observable<List<MovieFavoriteEntity>>
}