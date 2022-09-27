package com.zaki.movieapp.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class SessionUseCase @Inject constructor(
    @Named("login_datastore") private val dataStore: DataStore<Preferences>,
    @Named("login_username") private val dataStoreKey: Preferences.Key<String>
) {

    suspend fun saveSession(username: String) {
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = username
        }
    }

    fun getLoginSession(): Flow<String> = dataStore.data
        .map { preferences ->
            preferences[dataStoreKey] ?: ""
        }
}