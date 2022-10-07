package com.zaki.movieapp.data.repository

import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.data.local.entitiy.MovieFavoriteEntity
import com.zaki.movieapp.data.remote.RemoteDataSource
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.helper.Result
import com.zaki.movieapp.mapper.MovieMapper.toEntity
import com.zaki.movieapp.util.ConnectivityManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MovieRepository @Inject constructor(
  private val remoteDataSource: RemoteDataSource,
  private val localDataSource: LocalDataSource,
  private val connectivityManager: ConnectivityManager,
) {

  fun getMovies(): Observable<Result<List<MovieTrending>>> {
    return if (connectivityManager.isHasConnection()) {
      getMoviesFromApi()
    } else {
      getMoviesFromDB()
    }
  }

  private fun getMoviesFromApi(): Observable<Result<List<MovieTrending>>> {
    return remoteDataSource.getMoviesFromApi()
      .doOnNext { result ->
        if (result is Result.Success) {
          val movies = result.data.map { it.toEntity() }
          localDataSource.insertMovies(movies)
        }
      }
      .subscribeOn(Schedulers.io())
  }

  private fun getMoviesFromDB(): Observable<Result<List<MovieTrending>>> {
    return localDataSource.getMoviesFromDB()
  }

  fun getFavoriteMovies(): Observable<List<MovieTrending>> {
    return localDataSource.getFavoriteMovies()
  }

  fun getFavoriteMovie(movieId: Int): Observable<List<MovieTrending>> {
    return localDataSource.getFavoriteMovie(movieId)
  }

  suspend fun insertMovie(movie: MovieFavoriteEntity) {
    localDataSource.insertMovie(movie)
  }

  suspend fun deleteMovie(movie: MovieFavoriteEntity) {
    localDataSource.deleteMovie(movie)
  }
}