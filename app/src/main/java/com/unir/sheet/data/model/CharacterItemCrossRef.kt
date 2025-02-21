package com.unir.sheet.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

// Tabla de cruce
@Entity(
    tableName = "character_item_table",
    primaryKeys = ["characterId", "itemId"],
    foreignKeys = [
        ForeignKey(
            entity = RolCharacter::class,
            parentColumns = ["id"],
            childColumns = ["characterId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Item::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CharacterItemCrossRef(
    val characterId: Int,
    val itemId: String
)

