package com.zaki.movieapp.data.local

import com.zaki.movieapp.data.local.dao.AuthDao
import com.zaki.movieapp.data.local.entitiy.AuthEntity
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