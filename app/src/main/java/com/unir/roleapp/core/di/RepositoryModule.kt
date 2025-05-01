package com.unir.roleapp.core.di
import com.google.firebase.firestore.FirebaseFirestore
import com.unir.roleapp.adventure.data.repository.SceneRepository
import com.unir.roleapp.auth.data.dao.UserDao
import com.unir.roleapp.auth.data.repository.AuthRepositoryImpl
import com.unir.roleapp.auth.data.repository.UserRepositoryImpl
import com.unir.roleapp.auth.data.service.AuthApiService
import com.unir.roleapp.auth.data.service.UserApiService
import com.unir.roleapp.auth.domain.repository.AuthRepository
import com.unir.roleapp.auth.domain.repository.UserRepository
import com.unir.roleapp.auth.security.TokenManager
import com.unir.roleapp.core.data.MyDatabase
import com.unir.roleapp.character.data.dao.ItemDao
import com.unir.roleapp.character.data.dao.SkillDao
import com.unir.roleapp.character.data.repository.CharacterRepositoryImpl
import com.unir.roleapp.character.data.repository.ItemRepositoryImpl
import com.unir.roleapp.character.data.repository.SkillRepositoryImpl
import com.unir.roleapp.character.data.repository.SpellRepositoryImpl
import com.unir.roleapp.character.data.service.CharacterApiService
import com.unir.roleapp.character.data.service.ItemApiService
import com.unir.roleapp.character.data.service.SkillApiService
import com.unir.roleapp.character.data.service.SpellApiService
import com.unir.roleapp.character.domain.repository.CharacterRepository
import com.unir.roleapp.character.domain.repository.ItemRepository
import com.unir.roleapp.character.domain.repository.SkillRepository
import com.unir.roleapp.character.domain.repository.SpellRepository
import com.unir.roleapp.adventure.data.service.UserPreferences
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
        characterApiService: CharacterApiService,
        skillDao: SkillDao
    ): SkillRepository {
        return SkillRepositoryImpl(apiService, characterApiService,skillDao)
    }

    @Provides
    @Singleton
    fun provideSpellRepository(apiService: SpellApiService): SpellRepository {
        return SpellRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideSceneRepository(): SceneRepository {
        return SceneRepository()
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

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: AuthApiService,
        userDao: UserDao,
        tokenManager: TokenManager,
        userPreferences: UserPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(api, userDao, tokenManager, userPreferences)
    }



}