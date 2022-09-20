package com.zaki.movieapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.data.remote.api.MovieApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    fun provideMovieApiService(gson: Gson): MovieApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.themoviedb.org/3/")
            .build()
            .create(MovieApiService::class.java)
    }
}