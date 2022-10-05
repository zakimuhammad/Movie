package com.zaki.movieapp.data.repository

import com.google.gson.Gson
import com.zaki.movieapp.data.local.dao.MovieDao
import com.zaki.movieapp.data.local.entitiy.MovieFavoriteEntity
import com.zaki.movieapp.data.remote.api.MovieApiService
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.remote.response.MovieTrendingResponse
import com.zaki.movieapp.helper.ResponseError
import com.zaki.movieapp.helper.Result
import com.zaki.movieapp.mapper.MovieMapper.toEntity
import com.zaki.movieapp.mapper.MovieMapper.toMovieTrending
import com.zaki.movieapp.util.ConnectivityManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao,
    private val connectivityManager: ConnectivityManager,
    private val gson: Gson
) {

    fun getMovies(): Observable<Result<List<MovieTrending>>> {
        return if (connectivityManager.isHasConnection()) {
            getMoviesFromApi()
        } else {
            getMoviesFromDB()
        }
    }

    private fun getMoviesFromApi(): Observable<Result<List<MovieTrending>>> {
        return try {
            Observable.create {
                it.onNext(Result.Loading)
                val response = movieApiService.getTrendingMovie()
                response.enqueue(object : Callback<MovieTrendingResponse> {
                    override fun onResponse(
                        call: Call<MovieTrendingResponse>,
                        response: Response<MovieTrendingResponse>
                    ) {
                        if (response.isSuccessful) {
                            val movies = response.body()?.results ?: emptyList()
                            it.onNext(Result.Success(movies))
                        } else {
                            val jsonObject = JSONObject(response.errorBody()?.string()!!)
                            val responseError = gson.fromJson(jsonObject.toString(), ResponseError::class.java)
                            it.onNext(Result.Error(responseError.statusMessage.orEmpty()))
                        }
                    }

                    override fun onFailure(call: Call<MovieTrendingResponse>, t: Throwable) {
                        it.onNext(Result.Error(t.message.orEmpty()))
                    }
                })
            }
        } catch (t: Throwable) {
            Observable.just(Result.Error(t.message.orEmpty()))
        }
    }

    suspend fun insertMovies(movies: List<MovieTrending>) {
        if (connectivityManager.isHasConnection()) {
            val moviesEntity = movies.map { it.toEntity() }
            movieDao.insertMovies(moviesEntity)
        }
    }

    private fun getMoviesFromDB(): Observable<Result<List<MovieTrending>>> {
        return movieDao.getTrendingMovies()
            .subscribeOn(Schedulers.io())
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
            .observeOn(AndroidSchedulers.mainThread())
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