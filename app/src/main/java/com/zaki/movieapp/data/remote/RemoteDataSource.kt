package com.zaki.movieapp.data.remote

import com.zaki.movieapp.data.remote.api.MovieApiService
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieApiService: MovieApiService,
) {
}