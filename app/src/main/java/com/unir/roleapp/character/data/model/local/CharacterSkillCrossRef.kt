package com.roleapp.character.data.model.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "character_skill_table",
    primaryKeys = ["characterId", "skillId"],
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
            parentColumns = ["id"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Skill::class,
            parentColumns = ["id"],
            childColumns = ["skillId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("skillId")]
)
data class CharacterSkillCrossRef(
    val characterId: Long,
    val skillId: Int,
    var value: Int
)
