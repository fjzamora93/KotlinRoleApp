package com.unir.roleapp.adventure.domain.usecase

import com.unir.roleapp.adventure.data.repository.AdventureRepository
import javax.inject.Inject

class GetAdventureUseCaseImpl @Inject constructor(
    private val repo: AdventureRepository
) : GetAdventureUseCase {
    override suspend fun invoke(adventureId: String) =
        repo.getAdventure(adventureId)
}