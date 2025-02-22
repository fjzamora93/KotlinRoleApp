package com.unir.sheet.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "character_spell_table",
    primaryKeys = ["characterId", "spellId"],
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
            parentColumns = ["id"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Spell::class,
            parentColumns = ["id"],
            childColumns = ["spellId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CharacterSpellCrossRef(
    val characterId: Int,
    val spellId: String
)
