package com.unir.auth.data.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


import androidx.security.crypto.MasterKey

@Singleton
class TokenManagerEncrypted @Inject constructor(
    @ApplicationContext private val context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "SecureAuthPrefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private var accessToken: String? = null // Guardado en RAM

    // Guardar Access Token en memoria
    fun saveAccessToken(token: String) {
        accessToken = token
    }

    // Obtener Access Token desde memoria
    fun getAccessToken(): String? {
        return accessToken
    }

    // Guardar Refresh Token en EncryptedSharedPreferences
    fun saveRefreshToken(token: String) {
        prefs.edit().putString("refresh_token", token).apply()
    }

    // Obtener Refresh Token desde almacenamiento seguro
    fun getRefreshToken(): String? {
        return prefs.getString("refresh_token", null)
    }

    // Borrar todos los tokens (Logout)
    fun clearTokens() {
        accessToken = null
        prefs.edit().remove("refresh_token").apply()
    }
}
