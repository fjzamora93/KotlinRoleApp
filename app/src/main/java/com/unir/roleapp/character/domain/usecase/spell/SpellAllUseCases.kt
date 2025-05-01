package com.unir.roleapp.character.domain.usecase.spell

import com.unir.roleapp.character.data.model.local.CharacterEntity
import com.unir.roleapp.character.data.model.local.Spell
import com.unir.roleapp.character.domain.repository.SpellRepository
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
    suspend operator fun invoke(character: CharacterEntity): Result<List<Spell>> {
        val characterId = character.id ?: return Result.failure(Exception("El ID del personaje es nulo"))

        return repository.getSpellsByLevelAndRoleClass(character.level, character.rolClass.toString())
            .map { spells ->
                if (spells.isEmpty()) {
                    throw Exception("No se encontraron hechizos")
                }
                spells.map { spell ->
                    spell.copy(characterId = characterId)
                }
            }
    }
}