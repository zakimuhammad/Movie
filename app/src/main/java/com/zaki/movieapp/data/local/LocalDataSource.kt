package com.zaki.movieapp.data.local

import com.zaki.movieapp.data.local.dao.AuthDao
import com.zaki.movieapp.data.local.dao.MovieDao
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.mapper.MovieMapper.toMovieTrending
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val authDao: AuthDao
) {

    suspend fun insertUser(authEntity: AuthEntity) {
        authDao.insertUser(authEntity)
    }

    fun getUserLogin(username: String): AuthEntity? {
        return authDao.getUser(username)
    }
}