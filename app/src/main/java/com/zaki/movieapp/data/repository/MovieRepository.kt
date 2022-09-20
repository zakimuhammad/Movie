package com.zaki.movieapp.data.repository

import com.zaki.movieapp.data.local.LocalDataSource
import com.zaki.movieapp.data.remote.RemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
}