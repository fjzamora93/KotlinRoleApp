package com.unir.character.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.model.local.Skill
import com.unir.character.data.model.local.SkillValue

@Dao
interface SkillDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // Ignora si ya existe
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





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterSkills(skills: List<CharacterSkillCrossRef>)

    @Transaction
    suspend fun insertSkills(characterId: Long, skills: List<SkillValue>) {
        val crossRefs = skills.map { it.toCrossRef(characterId) }
        insertCharacterSkills(crossRefs)
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