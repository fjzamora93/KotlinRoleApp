package com.unir.character.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unir.character.data.model.local.Spell

@Dao
interface SpellDao {

    @Query("SELECT * FROM spellTable WHERE characterId = :characterId")
    suspend fun getSpellsByCharacter(characterId: Long): List<Spell>

    @Query("SELECT * FROM spellTable")
    suspend fun getSpellList(): List<Spell>

    @Query("SELECT * FROM spellTable WHERE id = :id")
    suspend fun getSpellById(id: String): Spell?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpell(spell: Spell)

    @Update
    suspend fun updateSpell(spell: Spell)

    @Delete
    suspend fun deleteSpell(spell: Spell)

}