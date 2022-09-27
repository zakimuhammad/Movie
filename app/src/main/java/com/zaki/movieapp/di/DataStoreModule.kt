package com.zaki.movieapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataStoreModule {

    @Provides
    @Named("login_username")
    @Singleton
    fun provideStringPreferencesLogin(): Preferences.Key<String> = stringPreferencesKey("login_username")

    @Provides
    @Named("login_datastore")
    @Singleton
    fun provideDataStoreLogin(
        context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("login_datastore")
        }
    }
}