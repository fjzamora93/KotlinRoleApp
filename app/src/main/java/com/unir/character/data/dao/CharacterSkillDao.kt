package com.unir.character.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.unir.character.data.model.local.CharacterSkillCrossRef

@Dao
interface CharacterSkillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterSkills: List<CharacterSkillCrossRef>)
}
