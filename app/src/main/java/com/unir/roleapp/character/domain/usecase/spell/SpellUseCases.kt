package com.roleapp.character.domain.usecase.spell

data class SpellUseCases(
    val getAllSpells: GetAllSpellsUseCase,
    val getSpellsByLevelAndRoleClass: GetSpellsByLevelAndRoleClassUseCase
)
