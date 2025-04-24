package com.roleapp.core.di
import com.google.firebase.firestore.FirebaseFirestore
import com.roleapp.adventure.data.repository.SceneRepository
import com.roleapp.auth.data.dao.UserDao
import com.roleapp.auth.data.repository.AuthRepositoryImpl
import com.roleapp.auth.data.repository.UserRepositoryImpl
import com.roleapp.auth.data.service.AuthApiService
import com.roleapp.auth.data.service.UserApiService
import com.roleapp.auth.domain.repository.AuthRepository
import com.roleapp.auth.domain.repository.UserRepository
import com.roleapp.auth.security.TokenManager
import com.roleapp.core.data.MyDatabase
import com.roleapp.character.data.dao.ItemDao
import com.roleapp.character.data.dao.SkillDao
import com.roleapp.character.data.repository.CharacterRepositoryImpl
import com.roleapp.character.data.repository.ItemRepositoryImpl
import com.roleapp.character.data.repository.SkillRepositoryImpl
import com.roleapp.character.data.repository.SpellRepositoryImpl
import com.roleapp.character.data.service.CharacterApiService
import com.roleapp.character.data.service.ItemApiService
import com.roleapp.character.data.service.SkillApiService
import com.roleapp.character.data.service.SpellApiService
import com.roleapp.character.domain.repository.CharacterRepository
import com.roleapp.character.domain.repository.ItemRepository
import com.roleapp.character.domain.repository.SkillRepository
import com.roleapp.character.domain.repository.SpellRepository
import com.unir.roleapp.adventure.data.repository.GameSessionRepositoryImpl
import com.unir.roleapp.adventure.domain.GameSessionRepository
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
        tokenManager: TokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(api, userDao, tokenManager)
    }


    @Singleton
    @Provides
    fun provideGameSessionRepository(
        firestore: FirebaseFirestore
    ): GameSessionRepository {
        return GameSessionRepositoryImpl(firestore)
    }

}