package com.unir.sheet.domain.repository

import com.unir.sheet.data.model.Spell

interface SpellRepository {

    // Obtener todos los hechizos
    suspend fun getAllSpells(): List<Spell>

    // Obtener hechizos por nivel y clase de rol (por ejemplo, "Magos" o "Clérigos")
    suspend fun getSpellsByLevelAndRoleClass(level: Int, roleClass: String): List<Spell>
}
