package com.zaki.movieapp

import android.app.Application
import com.zaki.movieapp.di.AppComponent
import com.zaki.movieapp.di.DaggerAppComponent

class MovieApplication : Application() {

  val appComponent: AppComponent by lazy {
    DaggerAppComponent.factory().create(this, applicationContext)
  }
}