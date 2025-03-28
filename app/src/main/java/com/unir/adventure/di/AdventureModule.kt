package com.unir.adventure.di

import com.data.FirebaseConfigManager
import com.unir.adventure.data.repository.SceneRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * TODO Inyección de dependencias para la sesión de juego (crear mejor un objeto de tipo Module para cada categoría)
 * - Repository
 * - Use Case
 * - Api SErvice
 * - Dao
 * */


@Module
@InstallIn(SingletonComponent::class)
object SceneModule {

    @Singleton
    @Provides
    fun provideSceneRepository(): SceneRepository {
        return SceneRepository()
    }
}
