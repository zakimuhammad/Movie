package com.zaki.movieapp.di

import com.zaki.movieapp.view.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class, DataStoreModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(signUpActivity: SignUpActivity)
    fun inject(signUpActivity: SignInActivity)
    fun inject(profileFragment: ProfileFragment)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(detailMovieActivity: DetailMovieActivity)
}