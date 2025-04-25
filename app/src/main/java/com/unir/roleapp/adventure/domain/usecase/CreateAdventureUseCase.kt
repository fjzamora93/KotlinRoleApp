package com.unir.roleapp.adventure.domain.usecase

import com.unir.roleapp.adventure.data.repository.AdventureRepositoryImpl
import com.unir.roleapp.adventure.domain.model.Adventure
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CreateAdventureUseCase {
    suspend operator fun invoke(request: CreateAdventureRequest): Result<Adventure>
}
