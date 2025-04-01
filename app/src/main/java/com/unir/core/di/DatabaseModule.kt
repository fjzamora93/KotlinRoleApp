package com.unir.core.di

import com.unir.auth.data.dao.UserDao
import com.unir.core.data.MyDatabase
import com.unir.character.data.dao.CharacterDao
import com.unir.character.data.dao.ItemDao
import com.unir.character.data.dao.SkillDao
import com.unir.character.data.dao.SpellDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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

    @Provides
    @Singleton
    fun provideSpellDao(database: MyDatabase): SpellDao {
        return database.getSpellDao()
    }


    @Provides
    @Singleton
    fun provideUserDao(database: MyDatabase): UserDao {
        return database.getUserDao()
    }
}