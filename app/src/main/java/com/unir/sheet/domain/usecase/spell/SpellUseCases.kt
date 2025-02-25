package com.unir.sheet.domain.usecase.spell

data class SpellUseCases(
    val getAllSpells: GetAllSpellsUseCase,
    val getSpellsByLevelAndRoleClass: GetSpellsByLevelAndRoleClassUseCase
)
