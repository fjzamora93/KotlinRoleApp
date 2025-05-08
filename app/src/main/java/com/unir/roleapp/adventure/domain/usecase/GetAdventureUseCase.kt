package com.unir.roleapp.adventure.domain.usecase

import com.unir.roleapp.adventure.data.repository.AdventureRepositoryImpl
import com.unir.roleapp.adventure.domain.model.Adventure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

fun interface GetAdventureUseCase {
    suspend operator fun invoke(adventureId: String): Result<Adventure>
}