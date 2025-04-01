package com.unir.core.di
import com.unir.adventure.data.repository.SceneRepository
import com.unir.auth.data.dao.UserDao
import com.unir.auth.data.repository.AuthRepositoryImpl
import com.unir.auth.data.repository.UserRepositoryImpl
import com.unir.auth.data.service.AuthApiService
import com.unir.auth.data.service.UserApiService
import com.unir.auth.domain.repository.AuthRepository
import com.unir.auth.domain.repository.UserRepository
import com.unir.auth.security.TokenManager
import com.unir.core.data.MyDatabase
import com.unir.character.data.dao.ItemDao
import com.unir.character.data.dao.SkillDao
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

}