package com.unir.sheet.data.local

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // Obtener el token almacenado usando el sessionManager inyectado
        val token = sessionManager.getToken()
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}

