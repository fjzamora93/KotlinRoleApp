package com.unir.core.di

import android.content.Context
import com.unir.auth.security.AuthInterceptor
import com.unir.auth.security.TokenManager
import com.unir.core.data.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/** INYECCIÓN DE DEPENDNECIAS PARA LA APLICACIÓN COMPLETA */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase {
        return MyDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }



}