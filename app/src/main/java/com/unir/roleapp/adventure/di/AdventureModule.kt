package com.unir.roleapp.adventure.di


import com.unir.roleapp.adventure.data.repository.AdventureRepositoryImpl
import com.unir.roleapp.adventure.data.repository.AdventureContextRepositoryImpl
import com.unir.roleapp.adventure.data.repository.AdventureRepository
import com.unir.roleapp.adventure.data.repository.AdventureScriptRepositoryImpl
import com.unir.roleapp.adventure.data.repository.DeleteAdventureUseCaseImpl
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureUseCase
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureUseCaseImpl
import com.unir.roleapp.adventure.domain.usecase.DeleteAdventureUseCase

import com.unir.roleapp.adventure.domain.usecase.SaveAdventureContextUseCase
import com.unir.roleapp.adventure.domain.usecase.GenerateAdventureScriptUseCase
import com.unir.roleapp.adventure.domain.usecase.GetAdventureUseCase
import com.unir.roleapp.adventure.domain.usecase.GetAdventureUseCaseImpl
import com.unir.roleapp.adventure.domain.usecase.SaveAdventureUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AdventureModule {

    // 1️⃣ Repository binding
    @Binds
    abstract fun bindAdventureRepository(
        impl: AdventureRepositoryImpl
    ): AdventureRepository

    // 2️⃣ Use-case bindings
    @Binds
    abstract fun bindCreateAdventureUseCase(
        impl: CreateAdventureUseCaseImpl
    ): CreateAdventureUseCase

    @Binds
    abstract fun bindGetAdventureUseCase(
        impl: GetAdventureUseCaseImpl
    ): GetAdventureUseCase

    @Binds
    abstract fun bindSaveAdventureContextUseCase(
        impl: AdventureContextRepositoryImpl
    ): SaveAdventureContextUseCase

    @Binds
    abstract fun bindDeleteAdventureUseCase(
        impl: DeleteAdventureUseCaseImpl
    ): DeleteAdventureUseCase

    companion object {
        // 3️⃣ Supply a Dispatcher.IO
        @Provides
        @Singleton
        @JvmStatic
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}