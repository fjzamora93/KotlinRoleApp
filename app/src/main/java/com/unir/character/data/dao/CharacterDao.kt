package com.unir.character.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.unir.character.data.model.local.CharacterEntity


@Dao
interface CharacterDao {

    // QUERYS SIMPLES
    @Query("SELECT * FROM character_entity_table")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Transaction
    @Query("SELECT * FROM character_entity_table WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Long): CharacterEntity?

    @Transaction
    @Query("SELECT * FROM character_entity_table WHERE userId = :userId")
    suspend fun getCharactersByUserId(userId: Int): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Delete
    suspend fun deleteCharacter(character: CharacterEntity)




}
