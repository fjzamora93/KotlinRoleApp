package com.unir.roleapp.adventure.domain.usecase

import com.unir.roleapp.adventure.data.repository.AdventureRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

fun interface DeleteAdventureUseCase {
    suspend operator fun invoke(adventureId: String): Result<Unit>
}
