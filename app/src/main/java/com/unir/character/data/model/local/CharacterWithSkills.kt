package com.unir.character.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithSkills(
    val character: CharacterEntity,
    val skillsWithValues: List<SkillWithValue>
)

data class SkillWithValue(
    val skill: Skill,
    val value: Int
)
