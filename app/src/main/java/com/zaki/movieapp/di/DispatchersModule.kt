package com.zaki.movieapp.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DispatchersModule {

    @Provides
    @ActivityScope
    fun provideDispatchersIo(): CoroutineDispatcher = Dispatchers.IO
}