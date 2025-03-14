package com.unir.auth.di

import com.data.MyDatabase
import com.unir.auth.data.dao.UserDao
import com.unir.auth.data.service.AuthApiService
import com.unir.auth.data.service.UserApiService
import com.unir.auth.security.AuthInterceptor
import com.unir.auth.security.TokenManager
import com.unir.character.data.dao.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {


    @Provides
    @Singleton
    fun provideUserDao(database: MyDatabase): UserDao {
        return database.getUserDao()
    }

    @Singleton
    @Provides
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }
}