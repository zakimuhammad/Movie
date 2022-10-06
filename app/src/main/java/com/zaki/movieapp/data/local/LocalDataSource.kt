package com.zaki.movieapp.data.local

import com.zaki.movieapp.data.local.dao.AuthDao
import com.zaki.movieapp.data.local.dao.MovieDao
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import com.zaki.movieapp.data.local.entitiy.MovieFavoriteEntity
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.helper.Result
import com.zaki.movieapp.mapper.MovieMapper.toMovieTrending
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
  private val authDao: AuthDao, private val movieDao: MovieDao
) {

  suspend fun insertUser(authEntity: AuthEntity) {
    authDao.insertUser(authEntity)
  }

  fun getUserLogin(username: String): Flow<AuthEntity?> {
    return authDao.getUser(username)
  }

  fun getMoviesFromDB(): Observable<Result<List<MovieTrending>>> {
    return movieDao.getTrendingMovies().subscribeOn(Schedulers.io()).map { movieTrendingEntities ->
        movieTrendingEntities.map { it.toMovieTrending() }
      }.map { Result.Success(it) }
  }

  suspend fun insertMovies(movies: List<MovieTrendingEntity>) {
    movieDao.insertMovies(movies)
  }

  fun getFavoriteMovies(): Observable<List<MovieTrending>> {
    return movieDao.getFavoriteMovies().subscribeOn(Schedulers.io()).map { movies ->
        movies.map { it.toMovieTrending() }
      }
  }

  fun getFavoriteMovie(movieId: Int): Observable<List<MovieTrending>> {
    return movieDao.getFavoriteMovie(movieId).subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread()).map { movies ->
        movies.map { it.toMovieTrending() }
      }
  }

  suspend fun insertMovie(movie: MovieFavoriteEntity) {
    movieDao.insertMovie(movie)
  }

  suspend fun deleteMovie(movie: MovieFavoriteEntity) {
    movieDao.deleteMovie(movie)
  }
}