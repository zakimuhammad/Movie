package com.zaki.movieapp.di

import android.app.Application
import androidx.room.Room
import com.zaki.movieapp.data.local.MovieDatabase
import com.zaki.movieapp.data.local.dao.AuthDao
import com.zaki.movieapp.data.local.dao.MovieDao
import dagger.Module
import dagger.Provides

@Module class DatabaseModule {

  @Provides @ActivityScope fun provideMovieDatabase(application: Application): MovieDatabase {
    return Room.databaseBuilder(application, MovieDatabase::class.java, "movie_database.db").build()
  }

  @Provides @ActivityScope fun provideMovieDao(database: MovieDatabase): MovieDao =
    database.movieDao()

  @Provides @ActivityScope fun provideAuthDao(database: MovieDatabase): AuthDao = database.authDao()
}