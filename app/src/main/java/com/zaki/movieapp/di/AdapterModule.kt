package com.zaki.movieapp.di

import com.zaki.movieapp.view.MovieAdapter
import dagger.Module
import dagger.Provides

@Module class AdapterModule {

  @Provides @ActivityScope fun provideMovieAdapter() = MovieAdapter()
}