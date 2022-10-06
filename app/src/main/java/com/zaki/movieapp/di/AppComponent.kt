package com.zaki.movieapp.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton @Component(modules = [AppSubcomponents::class, DataStoreModule::class])
interface AppComponent {

  @Component.Factory interface Factory {
    fun create(
      @BindsInstance application: Application, @BindsInstance context: Context
    ): AppComponent
  }

  fun authComponent(): AuthComponent.Factory
  fun mainComponent(): MainComponent.Factory
}