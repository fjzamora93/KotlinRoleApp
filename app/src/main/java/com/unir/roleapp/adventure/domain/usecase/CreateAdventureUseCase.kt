package com.unir.roleapp.adventure.domain.usecase

import com.unir.roleapp.adventure.domain.model.Adventure

interface CreateAdventureUseCase {
    suspend operator fun invoke(request: CreateAdventureRequest): Result<Adventure>
}
