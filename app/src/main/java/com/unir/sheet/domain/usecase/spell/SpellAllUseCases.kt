package com.unir.sheet.domain.usecase.spell

import com.unir.sheet.data.model.Spell
import com.unir.sheet.domain.repository.SpellRepository
import javax.inject.Inject

class GetAllSpellsUseCase @Inject constructor(
    private val repository: SpellRepository
) {
    suspend operator fun invoke(): Result<List<Spell>> {
        return repository.getAllSpells()
    }
}

class GetSpellsByLevelAndRoleClassUseCase @Inject constructor(
    private val repository: SpellRepository
) {
    suspend operator fun invoke(level: Int, roleClass: String): Result<List<Spell>> {
        return repository.getSpellsByLevelAndRoleClass(level, roleClass)
    }
}