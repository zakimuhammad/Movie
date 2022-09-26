package com.zaki.movieapp.data.repository

import com.zaki.movieapp.data.local.dao.MovieDao
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import com.zaki.movieapp.data.remote.api.MovieApiService
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.mapper.MovieMapper.toEntity
import com.zaki.movieapp.mapper.MovieMapper.toMovieTrending
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao,
) {

    fun getMovies(): Observable<List<MovieTrending>> {
        return getMoviesFromDB()
    }

    private fun getMoviesFromApi(): Observable<List<MovieTrending>> {
        return movieApiService.getTrendingMovie(MovieApiService.API_KEY)
            .map { it.results ?: emptyList() }
            .map { movies -> movies.map { it.toEntity() } }
            .doOnNext { movies ->
                movies.forEach {
                    movieDao.insertMovies(it)
                }
            }
            .map { movies -> movies.map { it.toMovieTrending() }}
    }

    private fun getMoviesFromDB(): Observable<List<MovieTrending>> {
        return movieDao.getTrendingMovies()
            .subscribeOn(Schedulers.io())
            .map { movieTrendingEntities ->
                movieTrendingEntities.map { it.toMovieTrending() }
            }
            .flatMap {
                return@flatMap if (it.isEmpty()) getMoviesFromApi()
                else Observable.just(it)
            }
    }

    fun getFavoriteMovies(): Observable<List<MovieTrending>> {
        return movieDao.getFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .map { movieTrendingEntities ->
                movieTrendingEntities.map { it.toMovieTrending() }
            }
    }

    suspend fun updateMovie(movie: MovieTrendingEntity) {
        movieDao.updateMovie(movie)
    }
}