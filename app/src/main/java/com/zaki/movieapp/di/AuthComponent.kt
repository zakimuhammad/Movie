package com.zaki.movieapp.di

import com.zaki.movieapp.view.SignInActivity
import com.zaki.movieapp.view.SignUpActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [DispatchersModule::class, DatabaseModule::class, NetworkModule::class])
interface AuthComponent {

  @Subcomponent.Factory interface Factory {
    fun create(): AuthComponent
  }

  fun inject(signUpActivity: SignUpActivity)
  fun inject(signUpActivity: SignInActivity)
}