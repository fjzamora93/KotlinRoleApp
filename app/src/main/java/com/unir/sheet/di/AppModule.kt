package com.unir.sheet.di

import android.content.Context
import com.unir.sheet.data.local.dao.CharacterDao
import com.unir.sheet.data.local.dao.ItemDao
import com.unir.sheet.data.local.dao.SkillDao
import com.unir.sheet.data.local.database.MyDatabase
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.data.repository.CharacterRepositoryImpl
import com.unir.sheet.data.repository.ItemRepositoryImpl
import com.unir.sheet.data.repository.SkillRepositoryImpl
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.ItemRepository
import com.unir.sheet.domain.usecase.character.CharacterUseCases
import com.unir.sheet.domain.usecase.character.DeleteCharacterUseCase
import com.unir.sheet.domain.usecase.character.GetAllCharactersUseCase
import com.unir.sheet.domain.usecase.character.GetCharacterByIdUseCase
import com.unir.sheet.domain.usecase.character.GetCharacterByUserIdUseCase
import com.unir.sheet.domain.usecase.character.InsertCharacterUseCase
import com.unir.sheet.domain.usecase.character.UpdateCharacterUseCase
import com.unir.sheet.domain.usecase.item.AddItemToCharacterUseCase
import com.unir.sheet.domain.usecase.item.DestroyItemUseCase
import com.unir.sheet.domain.usecase.item.FetchItemsUseCase
import com.unir.sheet.domain.usecase.item.GetItemsByCharacterId
import com.unir.sheet.domain.usecase.item.ItemUseCases
import com.unir.sheet.domain.usecase.item.SellItemUseCase
import com.unir.sheet.domain.usecase.skill.AddSkillToCharacterUseCase
import com.unir.sheet.domain.usecase.skill.DeleteSkillFromCharacterUseCase
import com.unir.sheet.domain.usecase.skill.GetAllSkillsUseCase
import com.unir.sheet.domain.usecase.skill.GetSkillsFromCharacterUseCase
import com.unir.sheet.domain.usecase.skill.SkillUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  AppModule {





    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase {
        return MyDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(
        database: MyDatabase,
        apiService: ApiService
    ): com.unir.sheet.domain.repository.CharacterRepository {
        return CharacterRepositoryImpl(
            database.characterDao(), apiService
        )
    }

    @Provides
    @Singleton
    fun provideItemRepository(
        apiService: ApiService,
        itemDao: ItemDao
    ): ItemRepository {
        return ItemRepositoryImpl(apiService, itemDao)
    }

    @Provides
    @Singleton
    fun provideSkillRepository(
        apiService: ApiService,
        skillDao: SkillDao
    ): SkillRepositoryImpl {
        return SkillRepositoryImpl(apiService, skillDao)
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: MyDatabase): CharacterDao {
        return database.characterDao()
    }
    @Provides
    @Singleton
    fun provideItemDao(database: MyDatabase): ItemDao {
        return database.getItemDao()
    }

    @Provides
    @Singleton
    fun provideSkillDao(database: MyDatabase): SkillDao {
        return database.getSkillDao()
    }

    // Proveer el CharacterUseCases
    @Provides
    @Singleton
    fun provideCharacterUseCases(characterRepository: CharacterRepository): CharacterUseCases {
        return CharacterUseCases(
            getAllCharacters = GetAllCharactersUseCase(characterRepository),
            getCharacterByUserId = GetCharacterByUserIdUseCase(characterRepository),
            getCharacterById = GetCharacterByIdUseCase(characterRepository),
            insertCharacter = InsertCharacterUseCase(characterRepository),
            updateCharacter = UpdateCharacterUseCase(characterRepository),
            deleteCharacter = DeleteCharacterUseCase(characterRepository),
        )
    }

    @Provides
    @Singleton
    fun provideItemUseCases(
        itemRepository: ItemRepository,
        characterRepository: CharacterRepository
    ): ItemUseCases {
        return ItemUseCases(
            fetchItems = FetchItemsUseCase(itemRepository),
            getItemsByCharacterId = GetItemsByCharacterId(itemRepository),
            sellItem = SellItemUseCase(characterRepository, itemRepository),
            destroyItem = DestroyItemUseCase(itemRepository),
            addItemToCharacter = AddItemToCharacterUseCase(characterRepository, itemRepository)
        )
    }

    // Dependency Injection in AppModule
    @Provides
    @Singleton
    fun provideSkillUseCases(repository: SkillRepositoryImpl): SkillUseCases {
        return SkillUseCases(
            getAllSkills = GetAllSkillsUseCase(repository),
            addSkillToCharacter = AddSkillToCharacterUseCase(repository),
            deleteSkillFromCharacter = DeleteSkillFromCharacterUseCase(repository),
            getSkillsFromCharacter = GetSkillsFromCharacterUseCase(repository)
        )
    }


}
