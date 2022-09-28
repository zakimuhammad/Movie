package com.zaki.movieapp.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class DataStoreUseCase @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @Named("login_username") private val loginKey: Preferences.Key<String>
) {

    suspend fun saveSession(username: String) {
        dataStore.edit { preferences ->
            preferences[loginKey] = username
        }
    }

    fun getLoginSession(): Flow<String> = dataStore.data
        .map { preferences ->
            preferences[loginKey] ?: ""
        }
}