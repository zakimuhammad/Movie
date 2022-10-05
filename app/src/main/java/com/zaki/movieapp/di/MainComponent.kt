package com.zaki.movieapp.di

import com.zaki.movieapp.view.DetailMovieActivity
import com.zaki.movieapp.view.FavoriteFragment
import com.zaki.movieapp.view.HomeFragment
import com.zaki.movieapp.view.ProfileFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [DispatchersModule::class, AdapterModule::class, DatabaseModule::class, NetworkModule::class])
interface MainComponent {
  @Subcomponent.Factory
  interface Factory {
    fun create(): MainComponent
  }

  fun inject(homeFragment: HomeFragment)
  fun inject(profileFragment: ProfileFragment)
  fun inject(favoriteFragment: FavoriteFragment)
  fun inject(detailMovieActivity: DetailMovieActivity)
}