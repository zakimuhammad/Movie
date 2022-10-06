package com.zaki.movieapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zaki.movieapp.data.remote.api.MovieApiService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module class NetworkModule {

  @Provides @ActivityScope fun provideOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val header = Interceptor {
      val request = it.request().newBuilder()
        .addHeader("Authorization", "Bearer ${MovieApiService.ACCESS_TOKEN}").build()
      it.proceed(request)
    }

    return OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(header).build()
  }

  @Provides @ActivityScope fun provideGson(): Gson {
    return GsonBuilder().setLenient().create()
  }

  @Provides @ActivityScope fun provideRetrofit(
    gson: Gson, okHttpClient: OkHttpClient
  ): Retrofit {
    return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build()
  }

  @Provides @ActivityScope fun provideMovieApiService(
    retrofit: Retrofit
  ): MovieApiService {
    return retrofit.create(MovieApiService::class.java)
  }
}