package com.unir.character.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.model.local.Skill


@Dao
interface CharacterDao {

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


    // MANEJO DE LAS RELACIONES
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSkills(skills: List<Skill>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacterSkills(characterSkills: List<CharacterSkillCrossRef>)




    @Query("SELECT * FROM SkillTable WHERE id = :skillId")
    suspend fun getSkillById(skillId: Int): Skill?

    // OBTENER HABILIDADES DE PERSONAJE
    @Query("SELECT * FROM character_skill_table WHERE characterId = :characterId")
    suspend fun getCharacterSkills(characterId: Long): List<CharacterSkillCrossRef>


}
