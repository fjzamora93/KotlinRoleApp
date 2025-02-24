package com.unir.sheet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.model.Spell

@Dao
interface SkillDao {
    @Query("SELECT * FROM skillTable")
    suspend fun getSkillList(): List<Skill>


}