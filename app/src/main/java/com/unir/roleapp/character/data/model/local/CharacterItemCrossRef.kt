package com.unir.roleapp.character.data.model.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "character_item_cross_ref",
    primaryKeys = ["characterId", "itemId"],
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
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
    ],
    indices = [Index(value = ["characterId"]), Index(value = ["itemId"])]
)
data class CharacterItemCrossRef(
    val characterId: Long,
    val itemId: Int,
    val quantity: Int = 1,
    val updatedAt: Long = System.currentTimeMillis()
)
