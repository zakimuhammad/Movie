package com.zaki.movieapp.data.repository

import com.zaki.movieapp.data.local.dao.MovieDao
import com.zaki.movieapp.data.local.entitiy.MovieFavoriteEntity
import com.zaki.movieapp.data.remote.api.MovieApiService
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.helper.Result
import com.zaki.movieapp.mapper.MovieMapper.toEntity
import com.zaki.movieapp.mapper.MovieMapper.toMovieTrending
import com.zaki.movieapp.util.ConnectivityManager
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao,
    private val connectivityManager: ConnectivityManager
) {

    fun getMovies(): Observable<Result<List<MovieTrending>>> {
        return if (connectivityManager.isHasConnection()) {
            getMoviesFromApi()
        } else {
            getMoviesFromDB()
        }
    }

    private fun getMoviesFromApi(): Observable<Result<List<MovieTrending>>> {
        return movieApiService.getTrendingMovie()
            .doOnError {
                Observable.just(Result.Error(it.localizedMessage.orEmpty()) )
            }
            .map {
                it.results ?: emptyList()
            }
            .map { movies -> movies.map { it.toEntity() } }
            .doOnNext { movies ->
                movies.forEach {
                    movieDao.insertMovies(it)
                }
            }
            .map { movies -> movies.map { it.toMovieTrending() }}
            .map { Result.Success(it) }
    }

    private fun getMoviesFromDB(): Observable<Result<List<MovieTrending>>> {
        return movieDao.getTrendingMovies()
            .subscribeOn(Schedulers.io())
            .doOnError { Result.Error(it.localizedMessage.orEmpty()) }
            .map { movieTrendingEntities ->
                movieTrendingEntities.map { it.toMovieTrending() }
            }
            .map { Result.Success(it) }
    }

    fun getFavoriteMovies(): Observable<List<MovieTrending>> {
        return movieDao.getFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .map { movies ->
                movies.map { it.toMovieTrending() }
            }
    }

    fun getFavoriteMovie(movieId: Int): Observable<List<MovieTrending>> {
        return movieDao.getFavoriteMovie(movieId)
            .subscribeOn(Schedulers.io())
            .map { movies ->
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