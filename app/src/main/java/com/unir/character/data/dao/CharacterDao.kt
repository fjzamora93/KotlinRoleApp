package com.unir.character.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.model.local.Skill
import com.unir.character.data.model.local.SkillValue


@Dao
interface CharacterDao {

    @Transaction
    @Query("SELECT * FROM character_entity_table WHERE id = :characterId")
    suspend fun getCharacterById(characterId: Long): CharacterEntity?

    @Transaction
    @Query("SELECT * FROM character_entity_table WHERE userId = :userId")
    suspend fun getCharactersByUserId(userId: Int): List<CharacterEntity>

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Upsert
    suspend fun insertCharacter(character: CharacterEntity) : Long

    @Upsert
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Delete
    suspend fun deleteCharacter(character: CharacterEntity)


    @Query("SELECT * FROM character_entity_table ORDER BY updatedAt DESC LIMIT 1")
    suspend fun getLastCharacter(): CharacterEntity?


    // OBTENER HABILIDADES DE PERSONAJE
    @Query("SELECT * FROM character_skill_table WHERE characterId = :characterId")
    suspend fun getCharacterSkills(characterId: Long): List<CharacterSkillCrossRef>



    /** TRANSACCIÓN ÚNICA PARA INSERTAR UN PERSONAJE CON SUS HABILIDADES */

    @Upsert
    suspend fun insertCharacterSkills(skills: List<CharacterSkillCrossRef>)

    @Transaction
    suspend fun insertCharacterWithSkills(
        character: CharacterEntity,
        skillsCrossRef: List<CharacterSkillCrossRef>
    ): CharacterEntity {
        // 1. Insertar el personaje
        val characterId = insertCharacter(character)

        // 2. Insertar las relaciones CharacterSkillCrossRef
        insertCharacterSkills(skillsCrossRef)

        // 3. Devolver el personaje con el ID generado
        return character.copy(id = characterId)
    }

}
