package com.unir.sheet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.CharacterSkillCrossRef
import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.model.Spell

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