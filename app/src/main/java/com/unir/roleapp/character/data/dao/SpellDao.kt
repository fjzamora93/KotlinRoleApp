package com.unir.roleapp.character.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.unir.roleapp.character.data.model.local.Spell

@Dao
interface SpellDao {

    @Query("SELECT * FROM spellTable WHERE characterId = :characterId")
    suspend fun getSpellsByCharacter(characterId: Long): List<Spell>

    @Query("SELECT * FROM spellTable")
    suspend fun getSpellList(): List<Spell>

    @Query("SELECT * FROM spellTable WHERE id = :id")
    suspend fun getSpellById(id: String): Spell?

    @Upsert
    suspend fun insertSpell(spell: Spell)

    @Upsert
    suspend fun updateSpell(spell: Spell)

    @Delete
    suspend fun deleteSpell(spell: Spell)

}