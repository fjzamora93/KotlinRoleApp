package com.unir.sheet.di

import android.app.Application
import androidx.room.Room
import com.unir.sheet.data.local.database.CharacterDao
import com.unir.sheet.data.local.database.ItemDao
import com.unir.sheet.data.local.database.MyDatabase
import com.unir.sheet.domain.usecase.character.CharacterUseCases
import com.unir.sheet.domain.usecase.character.DeleteCharacterUseCase
import com.unir.sheet.domain.usecase.character.GetAllCharactersUseCase
import com.unir.sheet.domain.usecase.character.GetCharacterByIdUseCase
import com.unir.sheet.domain.usecase.character.GetCharacterByUserIdUseCase
import com.unir.sheet.domain.usecase.character.GetCharacterWithRelationsUseCase
import com.unir.sheet.domain.usecase.character.InsertCharacterUseCase
import com.unir.sheet.domain.usecase.character.UpdateCharacterUseCase
import com.unir.sheet.util.Constants.MY_DATA_BASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  AppModule {




    @Provides
    @Singleton
    fun provideMyDataBase(app: Application): MyDatabase {
        return Room.databaseBuilder(
            app,
            MyDatabase::class.java,
            MY_DATA_BASE
        ).build()
    }



    @Provides
    @Singleton
    fun provideCharacterRepository(database: MyDatabase): com.unir.sheet.domain.repository.CharacterRepository {
        return com.unir.sheet.data.repository.CharacterRepositoryImpl(
            database.characterDao(),
            database.getItemDao()
        )
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
    fun provideCharacterUseCases(characterRepository: com.unir.sheet.domain.repository.CharacterRepository): CharacterUseCases {
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

}
