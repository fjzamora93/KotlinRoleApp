package com.unir.sheet.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.unir.sheet.data.local.dao.CharacterDao
import com.unir.sheet.data.local.dao.ItemDao
import com.unir.sheet.data.local.database.MyDatabase
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.data.repository.ItemRepositoryImpl
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.ItemRepository
import com.unir.sheet.domain.usecase.character.CharacterUseCases
import com.unir.sheet.domain.usecase.character.DeleteCharacterUseCase
import com.unir.sheet.domain.usecase.character.GetAllCharactersUseCase
import com.unir.sheet.domain.usecase.character.GetCharacterByIdUseCase
import com.unir.sheet.domain.usecase.character.GetCharacterByUserIdUseCase
import com.unir.sheet.domain.usecase.character.GetCharacterWithRelationsUseCase
import com.unir.sheet.domain.usecase.character.InsertCharacterUseCase
import com.unir.sheet.domain.usecase.character.UpdateCharacterUseCase
import com.unir.sheet.domain.usecase.item.AddItemToCharacterUseCase
import com.unir.sheet.domain.usecase.item.DestroyItemUseCase
import com.unir.sheet.domain.usecase.item.FetchItemsUseCase
import com.unir.sheet.domain.usecase.item.GetItemsByCharacterId
import com.unir.sheet.domain.usecase.item.ItemUseCases
import com.unir.sheet.domain.usecase.item.SellItemUseCase
import com.unir.sheet.util.Constants.MY_DATA_BASE
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
    fun provideCharacterRepository(database: MyDatabase): com.unir.sheet.domain.repository.CharacterRepository {
        return com.unir.sheet.data.repository.CharacterRepositoryImpl(
            database.characterDao(),
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
    fun provideSkillRepository(database: MyDatabase): com.unir.sheet.domain.repository.SkillRepository {
        return com.unir.sheet.data.repository.SkillRepository(
            database.characterDao(),
        )
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
            getCharacterWithRelations = GetCharacterWithRelationsUseCase(characterRepository)
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
            addItemToCharacter = AddItemToCharacterUseCase(characterRepository, itemRepository),
            sellItem = SellItemUseCase(characterRepository, itemRepository),
            destroyItem = DestroyItemUseCase(characterRepository, itemRepository),


        )
    }

}
