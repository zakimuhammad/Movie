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
    return try {
      Observable.create { emitter ->
        emitter.onNext(Result.Loading)
        val response = movieApiService.getTrendingMovie()
        response.enqueue(object : Callback<MovieTrendingResponse> {
          override fun onResponse(
            call: Call<MovieTrendingResponse>, response: Response<MovieTrendingResponse>
          ) {
            if (response.isSuccessful) {
              val movies = response.body()?.results ?: emptyList()
              emitter.onNext(Result.Success(movies))
            } else {
              response.errorBody()?.let {
                val jsonObject = JSONObject(it.string())
                val responseError = gson.fromJson(jsonObject.toString(), ErrorResponse::class.java)
                emitter.onNext(Result.Error(responseError.statusMessage.orEmpty()))
              }
            }
          }

          override fun onFailure(call: Call<MovieTrendingResponse>, t: Throwable) {
            emitter.onNext(Result.Error(t.message.orEmpty()))
          }
        })
      }
    } catch (t: Throwable) {
      Observable.just(Result.Error(t.message.orEmpty()))
    }
  }
}