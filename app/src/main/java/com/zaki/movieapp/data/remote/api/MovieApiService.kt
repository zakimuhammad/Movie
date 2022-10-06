package com.zaki.movieapp.data.remote.api

import com.zaki.movieapp.data.remote.response.MovieTrendingResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieApiService {

  companion object {
    const val ACCESS_TOKEN =
      "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNGNhZDNjYTVhZmE4MTQ2YWU4MDM3MTRhZGJmMjY1YiIsInN1YiI6IjVkNmE2ODk4OWQ4OTM5MDAxMjAxNGJkYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4yzHqW5MiE91Mk6d8tWuJLsuBVqmRhsDrfVu5rkVVCI"

    const val GET_TRENDING = "trending/movie/week"
  }

  @GET(GET_TRENDING) fun getTrendingMovie(): Call<MovieTrendingResponse>

}