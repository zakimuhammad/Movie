package com.zaki.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zaki.movieapp.data.local.dao.AuthDao
import com.zaki.movieapp.data.local.dao.MovieDao
import com.zaki.movieapp.data.local.entitiy.AuthEntity
import com.zaki.movieapp.data.local.entitiy.MovieFavoriteEntity
import com.zaki.movieapp.data.local.entitiy.MovieTrendingEntity

@Database(
    entities = [MovieTrendingEntity::class, AuthEntity::class, MovieFavoriteEntity::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun authDao(): AuthDao
}