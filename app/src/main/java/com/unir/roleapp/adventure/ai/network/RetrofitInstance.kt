package com.unir.roleapp.adventure.ai.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // tiempo para conectar
        .readTimeout(60, TimeUnit.SECONDS)    // tiempo para leer respuesta
        .writeTimeout(60, TimeUnit.SECONDS)   // tiempo para escribir petición
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://79.112.21.109:7860")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
