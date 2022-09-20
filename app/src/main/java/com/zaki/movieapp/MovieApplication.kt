package com.zaki.movieapp

import android.app.Application
import com.zaki.movieapp.di.AppComponent
import com.zaki.movieapp.di.AppModule
import com.zaki.movieapp.di.DaggerAppComponent
import com.zaki.movieapp.di.NetworkModule

class MovieApplication: Application() {

    val appComponent = DaggerAppComponent.create()

    override fun onCreate() {
        super.onCreate()
    }
}