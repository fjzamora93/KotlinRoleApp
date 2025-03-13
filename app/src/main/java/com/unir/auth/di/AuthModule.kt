package com.unir.auth.di

import com.unir.auth.data.service.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


/** METER AQUÍ INYECCIÓN DE DEPENDENCIAS DEL MÓDULO DE AUTENTIFICACIÓN
 * TODO:
 * - Repository.
 *
 *
 * */
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {


    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }
}