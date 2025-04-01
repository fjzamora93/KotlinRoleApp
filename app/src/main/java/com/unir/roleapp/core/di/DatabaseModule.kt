package com.roleapp.core.di

import com.roleapp.auth.data.dao.UserDao
import com.roleapp.core.data.MyDatabase
import com.roleapp.character.data.dao.CharacterDao
import com.roleapp.character.data.dao.ItemDao
import com.roleapp.character.data.dao.SkillDao
import com.roleapp.character.data.dao.SpellDao
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