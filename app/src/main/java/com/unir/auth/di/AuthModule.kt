package com.unir.auth.di

import com.unir.auth.data.repository.AuthRepositoryImpl
import com.unir.auth.data.service.AuthApiService
import com.unir.auth.domain.repository.AuthRepository
import com.unir.auth.domain.usecase.auth.AuthUseCases
import com.unir.auth.domain.usecase.auth.PostAutologinUseCase
import com.unir.auth.domain.usecase.auth.PostLoginUseCase
import com.unir.auth.domain.usecase.auth.PostLogoutUseCase
import com.unir.auth.domain.usecase.auth.PostSignupUseCase
import com.unir.auth.security.AuthInterceptor
import com.unir.auth.security.TokenManager
import com.unir.character.data.repository.SkillRepositoryImpl
import com.unir.character.domain.usecase.skill.AddDefaultSkills
import com.unir.character.domain.usecase.skill.DeleteSkillFromCharacterUseCase
import com.unir.character.domain.usecase.skill.GetAllSkillsUseCase
import com.unir.character.domain.usecase.skill.GetSkillsFromCharacterUseCase
import com.unir.character.domain.usecase.skill.SkillUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * Aquí se registran todas las inyecciones de dependencias (para que el @Inject constructor funcione, lo que se vaya a inyectar debe estar aquí dentro)
 * */
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: AuthApiService,
        tokenManager: TokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(api, tokenManager)
    }


    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            postLogin = PostLoginUseCase(repository),
            postSignup = PostSignupUseCase(repository),
            postLogout = PostLogoutUseCase(repository),
            postAutologin = PostAutologinUseCase(repository)
        )
    }

}