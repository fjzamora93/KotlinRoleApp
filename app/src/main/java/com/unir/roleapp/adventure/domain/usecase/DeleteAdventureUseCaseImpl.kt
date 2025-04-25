package com.unir.roleapp.adventure.domain.usecase

import com.unir.roleapp.adventure.data.repository.AdventureRepository
import javax.inject.Inject

class DeleteAdventureUseCaseImpl @Inject constructor(
    private val repo: AdventureRepository
) : DeleteAdventureUseCase {
    override suspend operator fun invoke(adventureId: String): Result<Unit> =
        runCatching { repo.deleteAdventure(adventureId) }
}