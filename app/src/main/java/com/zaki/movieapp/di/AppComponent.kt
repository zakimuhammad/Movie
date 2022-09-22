package com.zaki.movieapp.di

import com.zaki.movieapp.view.MainActivity
import com.zaki.movieapp.view.SignUpActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(signUpActivity: SignUpActivity)
}