package com.unir.auth.di

import com.data.MyDatabase
import com.unir.auth.data.dao.UserDao
import com.unir.auth.data.repository.AuthRepositoryImpl
import com.unir.auth.data.repository.UserRepositoryImpl
import com.unir.auth.data.service.AuthApiService
import com.unir.auth.data.service.UserApiService
import com.unir.auth.domain.repository.AuthRepository
import com.unir.auth.domain.repository.UserRepository
import com.unir.auth.domain.usecase.auth.AuthUseCases
import com.unir.auth.domain.usecase.auth.PostAutologinUseCase
import com.unir.auth.domain.usecase.auth.PostLoginUseCase
import com.unir.auth.domain.usecase.auth.PostLogoutUseCase
import com.unir.auth.domain.usecase.auth.PostSignupUseCase
import com.unir.auth.domain.usecase.user.DeleteUserUseCase
import com.unir.auth.domain.usecase.user.GetUserUseCase
import com.unir.auth.domain.usecase.user.UpdateUserUseCase
import com.unir.auth.domain.usecase.user.UserUseCase
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

    @Singleton
    @Provides
    fun provideUserRepository(
        api: UserApiService,
        tokenManager: TokenManager,
        userDao: UserDao
    ): UserRepository {
        return UserRepositoryImpl(api, tokenManager, userDao)
    }


    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository): UserUseCase {
        return UserUseCase(
            getUser = GetUserUseCase(repository),
            updateUser = UpdateUserUseCase(repository),
            deleteUser = DeleteUserUseCase(repository)
        )
    }
}