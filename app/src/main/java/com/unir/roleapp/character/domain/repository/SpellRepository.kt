package com.roleapp.character.domain.repository

import com.roleapp.character.data.model.local.Spell

interface SpellRepository {

    // Obtener todos los hechizos
    suspend fun getAllSpells(): Result<List<Spell>>

    // Obtener hechizos por nivel y clase de rol (por ejemplo, "Magos" o "Clérigos")
    suspend fun getSpellsByLevelAndRoleClass(level: Int, roleClass: String): Result<List<Spell>>


}
