package com.roleapp.auth.security

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


/** Añade el Access Token a todas las Request. Si no se puede añadir, aparecerá un log por consola */
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = tokenManager.getAccessToken()
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
            Log.d("AuthInterceptor", "Token añadido: Bearer $token")
        } else {
            Log.d("AuthInterceptor", "Token no encontrado")
        }
        return chain.proceed(requestBuilder.build())
    }
}
