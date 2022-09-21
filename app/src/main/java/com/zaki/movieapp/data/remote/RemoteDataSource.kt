package com.zaki.movieapp.data.remote

import com.zaki.movieapp.data.remote.api.MovieApiService
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.data.remote.response.MovieTrendingResponse
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val movieApiService: MovieApiService,
) {

    
}