package com.unir.character.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.model.local.Skill

@Dao
interface SkillDao {
    @Query("SELECT * FROM skillTable")
    suspend fun getSkillList(): List<Skill>

    // skills by character
    @Query("SELECT * FROM skillTable WHERE id IN (SELECT skillId FROM character_skill_table WHERE characterId = :characterId)")
    suspend fun getSkillsByCharacterId(characterId: Long): List<Skill>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(skills: List<Skill>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDefaultSkills(crossRefs: List<CharacterSkillCrossRef>)

    @Query("DELETE FROM character_skill_table WHERE characterId = :characterId")
    suspend fun deleteCharacterSkills(characterId: Long)

    @Transaction
    suspend fun updateCharacterSkills(characterId: Long, skillIds: List<Int>) : List<Skill> {
        deleteCharacterSkills(characterId)
        addDefaultSkills(skillIds.map { CharacterSkillCrossRef(characterId, it) })
        return getSkillsByCharacterId(characterId)

    }

}