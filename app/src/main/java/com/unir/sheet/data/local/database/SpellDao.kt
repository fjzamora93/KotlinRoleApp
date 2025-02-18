package com.unir.sheet.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unir.sheet.data.local.model.Item
import com.unir.sheet.data.local.model.Spell

@Dao
interface SpellDao {
    @Query("SELECT * FROM spellTable")
    suspend fun getSpellList(): List<Spell>

    @Query("SELECT * FROM spellTable WHERE id = :id")
    suspend fun getSpellById(id: String): Spell?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSpell(spell: Spell)

    @Update
    suspend fun updateSpell(spell: Spell)

    @Delete
    suspend fun deleteSpell(spell: Spell)

}