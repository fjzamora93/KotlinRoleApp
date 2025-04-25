package com.unir.roleapp.adventure.domain.usecase



import com.unir.roleapp.adventure.data.repository.AdventureRepository
import com.unir.roleapp.adventure.domain.model.Adventure
import javax.inject.Inject

class CreateAdventureUseCaseImpl @Inject constructor(
    private val repo: AdventureRepository
) : CreateAdventureUseCase {
    override suspend operator fun invoke(request: CreateAdventureRequest): Result<Adventure> =
        repo.createAdventure(request)
}