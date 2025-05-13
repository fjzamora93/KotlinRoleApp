package com.unir.roleapp.adventure.domain.usecase

import com.unir.roleapp.adventure.domain.model.Adventure

fun interface GetAdventureUseCase {
    suspend operator fun invoke(adventureId: String): Result<Adventure>
}