package com.zaki.movieapp.data.remote

import com.google.gson.Gson
import com.zaki.movieapp.data.remote.api.MovieApiService
import com.zaki.movieapp.data.remote.response.ErrorResponse
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.remote.response.MovieTrendingResponse
import com.zaki.movieapp.helper.Result
import io.reactivex.rxjava3.core.Observable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
  private val movieApiService: MovieApiService, private val gson: Gson
) {

  fun getMoviesFromApi(): Observable<Result<List<MovieTrending>>> {
    return movieApiService.getTrendingMovie()
      .map {
        if (it.isSuccessful) {
          val movies = it.body()?.results ?: emptyList()
          Result.Success(movies)
        } else {
          val jsonObject = JSONObject(it.errorBody()?.string())
          val responseError = gson.fromJson(jsonObject.toString(), ErrorResponse::class.java)
          Result.Error(responseError.statusMessage.orEmpty())
        }
      }
  }
}