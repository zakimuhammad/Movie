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
import kotlin.math.log

class SessionUseCase @Inject constructor(
    context: Context
) {
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "login_datastore")

        private val USERNAME_LOGIN = stringPreferencesKey("login_username")
    }

    private val loginDataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveSession(username: String) {
        loginDataStore.edit { pref ->
            pref[USERNAME_LOGIN] = username
        }
    }

    fun getLoginSession(): Flow<String> = loginDataStore.data
        .map { pref ->
            pref[USERNAME_LOGIN] ?: ""
        }
}