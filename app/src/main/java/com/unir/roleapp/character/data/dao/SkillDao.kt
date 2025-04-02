package com.roleapp.character.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.roleapp.character.data.model.local.Skill
import com.roleapp.character.data.model.local.SkillValue

@Dao
interface SkillDao {


    @Query("SELECT * FROM SkillTable")
    suspend fun getSkills(): List<Skill>


    @Upsert
    suspend fun insertSkill(skill: Skill): Long

    @Update
    suspend fun updateSkill(skill: Skill)

    // Método para insertar o actualizar habilidades
    suspend fun insertOrUpdateSkills(skills: List<Skill>) {
        for (skill in skills) {
            val id = insertSkill(skill) // Intenta insertar la habilidad
            if (id == -1L) { // Si la inserción falla (porque ya existe)
                updateSkill(skill) // Actualiza la habilidad existente
            }
        }
    }




    @Upsert
    suspend fun upsertCharacterSkills(skills: List<CharacterSkillCrossRef>)

    @Query("UPDATE character_entity_table SET updatedAt = :timestamp WHERE id = :characterId")
    suspend fun updateCharacterTimestamp(characterId: Long, timestamp: Long = System.currentTimeMillis())

    @Transaction
    suspend fun insertSkills(character: CharacterEntity, skills: List<SkillValue>) {
        val crossRefs = skills.map { it.toCrossRef(character.id) }
        upsertCharacterSkills(crossRefs)
        updateCharacterTimestamp(character.id)
    }


    // OBTENCIÓN DE TODAS LAS HABILIDADES DE UN PERSONAJE (TRNSACCIÓN EN TRES FASES)
    @Query("SELECT * FROM character_skill_table WHERE characterId = :characterId")
    suspend fun getCharacterSkills(characterId: Long): List<CharacterSkillCrossRef>

    @Query("SELECT * FROM SkillTable WHERE id = :skillId")
    suspend fun getSkillById(skillId: Int): Skill

    @Transaction
    suspend fun getSkillsWithValues(characterId: Long): List<SkillValue> {
        val crossRefs = getCharacterSkills(characterId)  // Paso 1: Obtener los CrossRefs
        return crossRefs.map { ref ->
            SkillValue(
                skill = getSkillById(ref.skillId),
                value = ref.value
            )
        }
    }


}