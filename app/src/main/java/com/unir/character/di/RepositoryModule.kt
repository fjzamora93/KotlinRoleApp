package com.unir.character.di
import com.unir.core.data.MyDatabase
import com.unir.character.data.dao.CharacterDao
import com.unir.character.data.dao.ItemDao
import com.unir.character.data.dao.SkillDao
import com.unir.character.data.dao.SpellDao
import com.unir.character.data.repository.CharacterRepositoryImpl
import com.unir.character.data.repository.ItemRepositoryImpl
import com.unir.character.data.repository.SkillRepositoryImpl
import com.unir.character.data.repository.SpellRepositoryImpl
import com.unir.character.data.service.CharacterApiService
import com.unir.character.data.service.ItemApiService
import com.unir.character.data.service.SkillApiService
import com.unir.character.data.service.SpellApiService
import com.unir.character.domain.repository.CharacterRepository
import com.unir.character.domain.repository.ItemRepository
import com.unir.character.domain.repository.SkillRepository
import com.unir.character.domain.repository.SpellRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(
        database: MyDatabase,
        apiService: CharacterApiService,
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            database.characterDao(), apiService
        )
    }

    @Provides
    @Singleton
    fun provideItemRepository(
        apiService: ItemApiService,
        itemDao: ItemDao
    ): ItemRepository {
        return ItemRepositoryImpl(apiService, itemDao)
    }

    @Provides
    @Singleton
    fun provideSkillRepository(
        apiService: SkillApiService,
        skillDao: SkillDao
    ): SkillRepository {
        return SkillRepositoryImpl(apiService, skillDao)
    }

    @Provides
    @Singleton
    fun provideSpellRepository(apiService: SpellApiService): SpellRepository {
        return SpellRepositoryImpl(apiService)
    }
}