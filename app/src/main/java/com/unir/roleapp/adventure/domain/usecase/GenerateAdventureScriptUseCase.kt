package com.unir.roleapp.adventure.domain.usecase

import com.unir.roleapp.adventure.data.repository.AdventureScriptRepositoryImpl
import com.unir.roleapp.adventure.domain.model.AdventureAct
import com.unir.roleapp.adventure.domain.model.AdventureSetup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

fun interface GenerateAdventureScriptUseCase {
    suspend operator fun invoke(setup: AdventureSetup): Result<List<AdventureAct>>
}