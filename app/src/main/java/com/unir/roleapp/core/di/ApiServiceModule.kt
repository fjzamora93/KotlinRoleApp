package com.unir.roleapp.core.di
import com.unir.roleapp.auth.data.service.AuthApiService
import com.unir.roleapp.auth.data.service.UserApiService
import com.unir.roleapp.character.data.service.CharacterApiService
import com.unir.roleapp.character.data.service.ItemApiService
import com.unir.roleapp.character.data.service.SkillApiService
import com.unir.roleapp.character.data.service.SpellApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {


    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterApiService(retrofit: Retrofit): CharacterApiService {
        return retrofit.create(CharacterApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideItemApiService(retrofit: Retrofit): ItemApiService {
        return retrofit.create(ItemApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideSpellApiService(retrofit: Retrofit): SpellApiService {
        return retrofit.create(SpellApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideSkillApiService(retrofit: Retrofit): SkillApiService {
        return retrofit.create(SkillApiService::class.java)
    }
}