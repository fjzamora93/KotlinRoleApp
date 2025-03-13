package com.unir.adventure.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent



/**
 * TODO Inyección de dependencias para la sesión de juego (crear mejor un objeto de tipo Module para cada categoría)
 * - Repository
 * - Use Case
 * - Api SErvice
 * - Dao
 * */
@Module
@InstallIn(SingletonComponent::class)
object AdventureModule {


}