package com.unir.sheet.data.local

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = sessionManager.getToken()
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
            // Añadir log para verificar que el token se está añadiendo
            Log.d("AuthInterceptor", "Token añadido: Bearer $token")
        } else {
            // Añadir log para verificar que el token no se está añadiendo
            Log.d("AuthInterceptor", "Token no encontrado")
        }
        return chain.proceed(requestBuilder.build())
    }
}
