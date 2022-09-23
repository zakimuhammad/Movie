package com.zaki.movieapp.data.repository

import com.zaki.movieapp.data.local.dao.MovieDao
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import com.zaki.movieapp.data.remote.api.MovieApiService
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.domain.ConnectivityManagerUseCase
import com.zaki.movieapp.mapper.MovieMapper.toEntity
import com.zaki.movieapp.mapper.MovieMapper.toMovieTrending
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.awaitResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao,
) {

    fun getMovies(): Observable<List<MovieTrending>> {
        movieApiService.getTrendingMovie(MovieApiService.API_KEY)
            .map { it.results ?: emptyList() }
            .doOnNext { movies ->
                val moviesEntity = movies.map { it.toEntity() }
                moviesEntity.forEach {
                    movieDao.insertMovies(it)
                }
            }

        return movieDao.getTrendingMovies()
            .subscribeOn(Schedulers.io())
            .map { movieTrendingEntities ->
                movieTrendingEntities.map { it.toMovieTrending() }
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