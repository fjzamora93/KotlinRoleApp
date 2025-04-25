package com.unir.roleapp.adventure.domain.usecase

import com.unir.roleapp.adventure.domain.model.AdventureAct

import com.unir.roleapp.adventure.data.repository.AdventureScriptRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

fun interface SaveAdventureUseCase {
    suspend operator fun invoke(adventureId: String, acts: List<AdventureAct>): Result<Unit>
}