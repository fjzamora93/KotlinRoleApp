package com.unir.sheet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.unir.sheet.data.model.CharacterWithItems
import com.unir.sheet.data.model.CharacterEntity


@Dao
interface CharacterDao {

    // QUERYS SIMPLES
    @Query("SELECT * FROM character_entity_table")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Transaction
    @Query("SELECT * FROM character_entity_table WHERE id = :characterId")
    suspend fun getCharacter(characterId: Int): CharacterEntity?

    @Insert
    suspend fun insertCharacter(character: CharacterEntity)

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Delete
    suspend fun deleteCharacter(character: CharacterEntity)




}
