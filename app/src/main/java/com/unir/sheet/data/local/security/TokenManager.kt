package com.unir.sheet.data.local.security


import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class TokenManager @Inject constructor(
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





//CÓDIGO ANTIGUO - EL QUE FUNCIONA GUARDADO EN SHARED PREFERNECES
//@Singleton
//class TokenManager @Inject constructor(
//    @ApplicationContext private val context: Context
//) {
//
//
//    private val prefs: SharedPreferences =
//        context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE)
//
//    fun saveToken(token: String) {
//        prefs.edit().putString("token", token).apply()
//    }
//
//    fun getToken(): String? {
//        return prefs.getString("token", null)
//    }
//
//    fun clearToken() {
//        prefs.edit().remove("token").apply()
//    }
//}
