package com.zaki.movieapp.data.remote.api

import com.zaki.movieapp.data.remote.response.MovieTrendingResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    companion object {
        const val API_KEY = "f4cad3ca5afa8146ae803714adbf265b"

        const val GET_TRENDING = "trending/all/week"
    }

    @GET(GET_TRENDING)
    fun getTrendingMovie(
        @Query("api_key") apiKey: String
    ): Single<MovieTrendingResponse>
}