package com.unir.sheet.data.local


import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        private val USER_TOKEN = stringPreferencesKey("user_token")
        private val USER_ID = stringPreferencesKey("user_id")
    }

    suspend fun saveUser(token: String, userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
            preferences[USER_ID] = userId
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }

    val userToken: Flow<String?> = context.dataStore.data.map { it[USER_TOKEN] }

    suspend fun getToken(): String? {
        return context.dataStore.data.map { it[USER_TOKEN] }.firstOrNull()
    }
}
