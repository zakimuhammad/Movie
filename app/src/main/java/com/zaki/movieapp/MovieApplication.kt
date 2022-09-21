package com.zaki.movieapp

import android.app.Application
import com.zaki.movieapp.di.*

class MovieApplication: Application() {

    val appComponent: AppComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .networkModule(NetworkModule())
        .databaseModule(DatabaseModule())
        .build()
}