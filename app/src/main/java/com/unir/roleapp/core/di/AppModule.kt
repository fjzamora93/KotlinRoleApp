package com.roleapp.core.di

import android.content.Context
import com.roleapp.auth.security.AuthInterceptor
import com.roleapp.auth.security.TokenManager
import com.roleapp.core.data.MyDatabase
import com.unir.roleapp.adventure.data.service.UserPreferences
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

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }


}