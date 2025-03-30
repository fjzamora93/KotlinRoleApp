package com.unir.character.di
import com.unir.core.data.MyDatabase
import com.unir.character.data.dao.CharacterDao
import com.unir.character.data.dao.ItemDao
import com.unir.character.data.dao.SkillDao
import com.unir.character.data.dao.SpellDao
import com.unir.character.data.service.CharacterApiService
import com.unir.character.data.service.ItemApiService
import com.unir.character.data.service.SkillApiService
import com.unir.character.data.service.SpellApiService
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