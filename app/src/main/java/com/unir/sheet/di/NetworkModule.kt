package com.unir.sheet.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.unir.sheet.data.local.AuthInterceptor
import com.unir.sheet.data.local.SessionManager
import com.unir.sheet.data.remote.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// https://springbootroleplay-production.up.railway.app/api/

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //private const val BASE_URL = "https://springbootroleplay-production.up.railway.app/api/"
    private const val BASE_URL = "http://192.168.1.200:8080/api/"

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(sessionManager: SessionManager): AuthInterceptor {
        return AuthInterceptor(sessionManager)
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS) // Aumenta el tiempo de espera de conexión a 30 segundos
            .readTimeout(30, TimeUnit.SECONDS)    // Aumenta el tiempo de espera de lectura a 30 segundos
            .writeTimeout(30, TimeUnit.SECONDS)   // Aumenta el tiempo de espera de escritura a 30 segundos
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
